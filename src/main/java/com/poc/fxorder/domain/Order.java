/*
 * No Copyright intended or License applies just for templating.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.poc.fxorder.domain;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import com.datastax.driver.core.DataType;

/**
 * Class OrderData
 * 
 * The model class to represent an Order
 * 
 * @author PM
 *
 */

@Table("orders")
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;

	@PrimaryKeyColumn(name = "uuid", type = PrimaryKeyType.PARTITIONED)
	private UUID uuid;

	@Indexed
	@PrimaryKeyColumn(name = "orderId", type = PrimaryKeyType.PARTITIONED)
	@Column(value = "orderId")
	private String orderId;

	/** The currency pair. */
	@Column(value = "currency")
	private String currency;

	/** The trade price. */
	@Column(value = "price")
	private Double price;

	/** The trade amount. */
	@Column(value = "amount")
	private long amount;

	/** The BID or ASK. */
	@Column(value = "orderType")
	private String orderType;

	/** The date when order recorded. */
	@Column(value = "orderDate")
	private long orderDate;

	/**
	 * @param id
	 * @param currency
	 * @param price
	 * @param amount
	 * @param orderType
	 * @param orderDate
	 */
	public Order(String orderId, String currency, Double price, long amount, String orderType, long orderDate) {
		super();
		this.orderId = orderId;
		this.currency = currency;
		this.price = price;
		this.amount = amount;
		this.orderType = orderType;
		this.orderDate = orderDate;
	}


	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency
	 *            the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * @return the amount
	 */
	public long getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(long amount) {
		this.amount = amount;
	}

	/**
	 * @return the orderType
	 */
	public String getOrderType() {
		return orderType;
	}

	/**
	 * @param orderType
	 *            the orderType to set
	 */
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	/**
	 * @return the orderDate
	 */
	public long getOrderDate() {
		return orderDate;
	}

	/**
	 * @param orderDate
	 *            the orderDate to set
	 */
	public void setOrderDate(long orderDate) {
		this.orderDate = orderDate;
	}



	/**
	 * @return the uuid
	 */
	public UUID getUuid() {
		return uuid;
	}



	/**
	 * @param uuid the uuid to set
	 */
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}



	/**
	 * @return the orderid
	 */
	public String getOrderId() {
		return orderId;
	}



	/**
	 * @param orderid the orderid to set
	 */
	public void setOrderid(String orderId) {
		this.orderId = orderId;
	}


}
