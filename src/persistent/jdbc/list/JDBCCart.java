package persistent.jdbc.list;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import common.exception.AlertDriver;
import common.exception.ErrorConnectionException;
import common.exception.UnknownIDProductException;
import common.factory.ProductFactory;
import common.factory.jdbcFactory.JDBCProductFactory;
import common.jdbc.JDBCComponent;
import persistent.list.Cart;

public class JDBCCart extends Cart {
	private JDBCComponent component = null;
	ProductFactory productFactory = new JDBCProductFactory();
	
	public JDBCCart(int IDUser) throws ErrorConnectionException, AlertDriver {
		super(IDUser);
		this.component = new JDBCComponent() ;
		
		this.setLabel("Cart");	
		
		
		
		
		try {
			ResultSet result = this.component.select(
					"*", 
					"wishlist", 
					"id_user = '" + IDUser + "' AND label = 'Cart' " );
			
			if (result.first()) {
				this.IDWishList = result.getInt("id_wishlist");
				
				//we get all items of the wish list.
				 result = this.component.select(
						"i.id_wishlist, i.id_product, i.quantity, i.unitPrice, w.label", 
						"wishlist w, item_wishlist i", 
						"w.id_user = '" + IDUser + "' AND i.id_wishlist = w.id_wishlist AND w.label = 'Cart' " );
				
				//we add all the elements found to the set
				 while(result.next()) {
					try {
						//construct a product of the wish list
						this.addElement( String.valueOf(result.getInt("id_product")) ,
								productFactory.buildProductWishList(result.getInt("id_product"), result.getInt("id_wishlist"), 
										result.getInt("quantity"), result.getFloat("unitPrice")));
						
					} catch (UnknownIDProductException e) {
						System.err.println("Impossible to create the product with id : " + result.getInt("id_product"));
						e.printStackTrace();
					} 
				} 
			} else {
				this.component.insert("wishlist(id_user, label)", "'" + this.IDUser + "', 'Cart'");
				result = this.component.select(
						"*", 
						"wishlist", 
						"id_user = '" + IDUser + "' AND label = 'Cart' " );
				
				if (result.first()) {
					this.IDWishList = result.getInt("id_wishlist");
					this.IDUser = IDUser;
					this.label = "Cart";
				} 
			}
			
			
			
		} catch (SQLException e) {
			throw new ErrorConnectionException();
		} 
		
		
	}

	@Override
	public Boolean isExisting() throws Exception {
		this.component = new JDBCComponent();
		ResultSet result = this.component.select("*", "wishlist "
				, "id_user = " +   this.IDUser + " AND label = 'Cart'"  );
		return result != null;
	}
	
	public Boolean isExisting(int IDUser) throws Exception {
		this.IDUser = IDUser;
		return this.isExisting();
	}


	@Override
	public void insert() throws Exception {
		
		
	}

	@Override
	public void update() throws Exception {
		// TODO JDBCCart Auto-generated method stub
		
	}

	@Override
	public void delete() throws Exception {
		// TODO JDBCCart Auto-generated method stub
		
	}

	@Override
	public Boolean hasChanged() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadFromKeys(List<String> columnNames, List<String> columnValues) throws Exception {
		
	}
}
