/*
 * No Copyright intended or License applies just for templating.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.poc.fxorder.kafka.gateway;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.fxorder.domain.Order;
import com.poc.fxorder.dto.OrderData;
import com.poc.fxorder.service.IFXTradeProcessingService;

/**
 * Class KafkaPubSubHandler
 * 
 * @author PM
 *
 */

@Configuration
public class KafkaPubSubHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaPubSubHandler.class);

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	IFXTradeProcessingService tradeProcessingService;

	@Value("${kafka.order.responseOrder.topic}")
	private String orderResponseTopic;

	private CountDownLatch latch1 = new CountDownLatch(1);

	public CountDownLatch getLatch1() {
		return latch1;
	}

	private CountDownLatch latch2 = new CountDownLatch(1);

	public CountDownLatch getLatch2() {
		return latch2;
	}

	/**
	 * KafkaPubSubHandler.receiveOrderCaptured(String)
	 * 
	 * @throws none
	 */

	@KafkaListener(topics = "tradeCapture")
	public void receiveOrderCaptured(String payload) {

		OrderData order = null;

		try {
			JSONObject jsonObject = new JSONObject(payload);

			order = new OrderData((String) jsonObject.get("id"), (String) jsonObject.get("currency"),
					(double) jsonObject.get("price"), (int) jsonObject.get("amount"),
					(String) jsonObject.get("orderType"), (long) jsonObject.get("orderDate"));

		} catch (JSONException e) {
			LOGGER.info("error is processing message: " + e.getMessage());
		}

		if (null != order) {

			boolean flag = tradeProcessingService.recordOrder(order);

			if (flag) {
				LOGGER.info("message has been processed successfully: " + order.getId());
			} else {
				LOGGER.info("error processing message: " + order.getId());
			}

		}

		latch1.countDown();

	}

	/**
	 * KafkaPubSubHandler.getMatchingOrders(String)
	 * 
	 * @throws none
	 */

	@KafkaListener(topics = "tradeRequest")
	public void getMatchingOrders(String payload) {

		String orderID = null;

		try {
			JSONObject jsonObject = new JSONObject(payload);
			orderID = (String) jsonObject.get("id");

		} catch (JSONException e) {
			LOGGER.info("error is processing message: " + e.getMessage());
		}

		if (null != orderID) {
			
			

			Optional<Order> order = tradeProcessingService.getById(orderID);

			if (order.isPresent()) {
				// Stringify the object
				ObjectMapper mapper = new ObjectMapper();
				String orderString;
				try {
					orderString = mapper.writeValueAsString(order.get());
					kafkaTemplate.send(orderResponseTopic, orderString);
					LOGGER.info("message searchedById: " + orderString);
				} catch (JsonProcessingException e) {
					LOGGER.error("unable to send message='{}'", order.get().getUuid(), e.getMessage());
				}

			}

		}

		latch2.countDown();

	}

}
