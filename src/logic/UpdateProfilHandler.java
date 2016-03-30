package logic;
/**
 * @author nassim vachor
 * @version 1.0
 * @since 2016-03-21
 */

import common.exception.AlertDriver;
import common.exception.ErrorConnectionException;
import common.exception.UnknownIDSellerException;
import common.factory.ProfilFactory;
import common.factory.jdbcFactory.JDBCProfilFactory;
import persistent.Seller;

public class UpdateProfilHandler {
	
	private Seller seller = null;	
	
	ProfilFactory ProfilFactory = new JDBCProfilFactory();
	
	public void createSeller(int ID, String nameShop, String description, String siret, String website) throws ErrorConnectionException, AlertDriver, UnknownIDSellerException {
		
		this.seller = ProfilFactory.buildSeller( ID, nameShop, description, siret, website);
	}
	
	public String getNameShop( ) throws UnknownIDSellerException, ErrorConnectionException, AlertDriver {
		
		return this.seller.getNameShop();
	}
	
	

	public void SetNameShop(String name) throws ErrorConnectionException, UnknownIDSellerException, AlertDriver{
	
		this.seller.setNameShop(name);
	}
	
	
	public String getSiret( ) throws UnknownIDSellerException, ErrorConnectionException, AlertDriver {
		return this.seller.getSiret();
	} 
		
		
		
		
		
	public void SetSiert(String siret) throws ErrorConnectionException, UnknownIDSellerException, AlertDriver{
		
		this.seller.setSiret(siret);
	}
	
	
	
	
	public String getWebsite( ) throws UnknownIDSellerException, ErrorConnectionException, AlertDriver {
		return this.seller.getWebsite();
	}
	
	
	
	
	public void settWebsite( String site) throws UnknownIDSellerException, ErrorConnectionException, AlertDriver {
		
		this.seller.setWebsite(site);
		}
	public String getDescription () throws ErrorConnectionException, UnknownIDSellerException, AlertDriver{
		return this.seller.getDescription();
	}
	public void setDescription(String desc) throws UnknownIDSellerException,ErrorConnectionException, AlertDriver {
		this.seller.setDescription(desc);
	}
	
	
	public void Validate() throws Exception{
		if (seller != null){
		this.seller.update();
		}
	}
}