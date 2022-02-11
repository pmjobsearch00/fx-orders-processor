/*
 * No Copyright intended or License applies just for templating.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.poc.fxorder.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datastax.driver.core.utils.UUIDs;
import com.poc.fxorder.domain.Order;
import com.poc.fxorder.dto.OrderData;
import com.poc.fxorder.exception.ServiceException;
import com.poc.fxorder.repository.OrderRecordsRepository;

/**
 * Class SimpleFXTradeProcessingService
 * 
 * It provide below five service functionalities
 * 
 * recordOrder(OrderData) and getById(String)
 * 
 * @author PM
 *
 */

@Service
public class SimpleFXTradeProcessingService implements IFXTradeProcessingService {
	
	@Autowired
	private OrderRecordsRepository repository;

	/**
	 * SimpleFXTradeProcessingService.saveOrder(OrderData)
	 * 
	 * @throws ServiceException
	 */
	@Override
	public boolean recordOrder(OrderData order) 
			throws ServiceException {
		
		Order orderItem = new Order(order.getId(), order.getCurrency(), order.getPrice(), order.getAmount(), 
				order.getOrderType(), order.getOrderDate());
		
		orderItem.setUuid(UUIDs.timeBased());
		Order saved = repository.save(orderItem);
		
		if (saved != null) {
			return true;
		} else {
			return false;
		}

	}
	
	
	/**
	 * SimpleFXTradeProcessingService.getById(String)
	 * 
	 * @throws ServiceException
	 */
	@Override
	public Optional<Order> getById(String oid) throws ServiceException {
		return repository.findByOrderId(oid);
	}

	

}