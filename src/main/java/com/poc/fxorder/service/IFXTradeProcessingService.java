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

import com.poc.fxorder.domain.Order;
import com.poc.fxorder.dto.OrderData;
import com.poc.fxorder.exception.ServiceException;

/**
 * Interface IFXTradeProcessingService
 * 
 * It provides below four service functionalities
 * 
 * recordOrder(OrderData) and getById(String)
 * 
 * @author PM
 *
 */

public interface IFXTradeProcessingService {

	/**
	 * Saves an order.
	 * @param an order
	 * @return the boolean status
	 * @throws ServiceException
	 */
	boolean recordOrder(OrderData order) 
			throws ServiceException;
	
	/**
	 * Returns the matching orders.
	 * @param order id string
	 * @return the matching OrderData list
	 * @throws ServiceException
	 */
	Optional<Order> getById(String id) throws ServiceException;
	
}