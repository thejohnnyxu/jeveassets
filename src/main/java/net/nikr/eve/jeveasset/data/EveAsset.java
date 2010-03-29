/*
 * Copyright 2009, 2010
 *    Niklas Kyster Rasmussen
 *    Flaming Candle*
 *
 *  (*) Eve-Online names @ http://www.eveonline.com/
 *
 * This file is part of jEveAssets.
 *
 * jEveAssets is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * jEveAssets is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with jEveAssets; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */
package net.nikr.eve.jeveasset.data;

import java.util.List;
import java.util.Vector;
import net.nikr.eve.jeveasset.gui.shared.Formater;

public class EveAsset implements Comparable<EveAsset> {

	private final static String PRICE_SELL_MAX = "Sell Maximum";
	private final static String PRICE_SELL_AVG = "Sell Average";
	private final static String PRICE_SELL_MEDIAN = "Sell Median";
	private final static String PRICE_SELL_MIN = "Sell Minimum";
	private final static String PRICE_MIDPOINT = "Midpoint";
	private final static String PRICE_BUY_MAX = "Buy Maximum";
	private final static String PRICE_BUY_AVG = "Buy Average";
	private final static String PRICE_BUY_MEDIAN = "Buy Median";
	private final static String PRICE_BUY_MIN = "Buy Minimum";
	
	
	//Default
	private static String defaultPriceType = PRICE_MIDPOINT;

	private static String priceType = defaultPriceType;

	private List<EveAsset> assets = new Vector<EveAsset>();
	private String name;
	private String group;
	private String category;
	private String owner;
	private long count;
	private String location;
	private int locationID;
	private String container;
	private String flag;
	private double priceBase;
	private String meta;
	private long id;
	private int typeId;
	private boolean marketGroup;
	private PriceData priceData;
	private UserPrice userPrice;
	private boolean corporationAsset;
	private float volume;
	private String region;
	private long typeCount = 0;
	private boolean bpo;
	private boolean singleton;
	private String security;
	private double priceReprocessed;

	public EveAsset(String name, String group, String category, String owner, long count, String location, String container, String flag, double priceBase, String meta, long id, int typeId, boolean marketGroup, boolean corporationAsset, float volume, String region, int locationID, boolean singleton, String security) {
		this.name = name;
		this.group = group;
		this.category = category;
		this.owner = owner;
		this.count = count;
		this.location = location;
		this.container = container;
		this.flag = flag;
		this.priceBase = priceBase;
		this.meta = meta;
		this.id = id;
		this.typeId = typeId;
		this.marketGroup = marketGroup;
		this.corporationAsset = corporationAsset;
		this.volume = volume;
		this.region = region;
		this.locationID = locationID;
		this.bpo = false;
		this.singleton = singleton;
		this.security = security;
	}

	public void setPriceData(PriceData priceData) {
		this.priceData = priceData;
	}

	public void setUserPrice(UserPrice userPrice) {
		this.userPrice = userPrice;
	}

	public boolean isBpo() {
		return bpo;
	}

	public void setBpo(boolean bpo) {
		this.bpo = bpo;
	}

	public boolean isBlueprint() {
		return (name.toLowerCase().contains("blueprint"));
	}

	public void addEveAsset(EveAsset eveAsset) {
		assets.add(eveAsset);
	}

	public List<EveAsset> getAssets() {
		return assets;
	}

	public String getCategory() {
		return category;
	}

	public String getContainer() {
		return container;
	}

	public boolean isCorporationAsset() {
		return corporationAsset;
	}

	public long getCount() {
		return count;
	}

	public String getFlag() {
		return flag;
	}

	public String getGroup() {
		return group;
	}

	public long getId() {
		return id;
	}

	public String getLocation() {
		return location;
	}

	public int getLocationID() {
		return locationID;
	}

	public boolean isMarketGroup() {
		return marketGroup;
	}

	public String getSecurity() {
		return security;
	}

	public PriceData getPriceData() {
		return priceData;
	}

	public UserPrice getUserPrice() {
		return userPrice;
	}

	public String getMeta() {
		return meta;
	}

	public String getName() {
		return name;
	}

	public String getOwner() {
		return owner;
	}

	public boolean isSingleton() {
		return singleton;
	}

	public double getPrice() {
		if (isBlueprint() && !isBpo()) {
			return 0;
		}

		if (this.getUserPrice() != null) {
			return this.getUserPrice().getPrice();
		}
		return getDefaultPrice();
	}

	public boolean isUserPrice() {
		return (this.getUserPrice() != null);
	}

	public double getDefaultPrice() {
		if (this.isMarketGroup()) {
			return getDefaultPrice(getPriceData());
		}
		return 0;
	}

	public static double getDefaultPrice(PriceData priceData) {
		if (priceData != null) {
			if (priceType.equals(PRICE_SELL_MAX)) return priceData.getSellMax();
			if (priceType.equals(PRICE_SELL_AVG)) return priceData.getSellAvg();
			if (priceType.equals(PRICE_SELL_MEDIAN)) return priceData.getSellMedian();
			if (priceType.equals(PRICE_SELL_MIN)) return priceData.getSellMin();
			if (priceType.equals(PRICE_MIDPOINT)) return (priceData.getSellMin() + priceData.getBuyMax()) / 2;
			if (priceType.equals(PRICE_BUY_MAX)) return priceData.getBuyMax();
			if (priceType.equals(PRICE_BUY_AVG)) return priceData.getBuyAvg();
			if (priceType.equals(PRICE_BUY_MEDIAN)) return priceData.getBuyMedian();
			if (priceType.equals(PRICE_BUY_MIN)) return priceData.getBuyMin();
		}
		return 0;
	}

	public static String getDefaultPriceType(){
		return defaultPriceType;
	}

	public double getPriceSellMin() {
		if (isBlueprint() && !isBpo()) {
			return 0;
		}
		if (this.isMarketGroup() && this.getPriceData() != null) {
			return this.getPriceData().getSellMin();
		}
		return 0;
	}

	public double getPriceBuyMax() {
		if (isBlueprint() && !isBpo()) {
			return 0;
		}
		if (this.isMarketGroup() && this.getPriceData() != null) {
			return this.getPriceData().getBuyMax();
		}
		return 0;
	}

	public double getPriceReprocessed() {
		return priceReprocessed;
	}

	public void setPriceReprocessed(double priceReprocessed) {
		this.priceReprocessed = priceReprocessed;
	}

	public double getValue() {
		return Formater.numberDouble(this.getPrice() * this.getCount());
	}

	public double getValueReprocessed() {
		return Formater.numberDouble(this.getPriceReprocessed() * this.getCount());
	}

	public double getPriceBase() {
		return priceBase;
	}

	public int getTypeId() {
		return typeId;
	}

	public String getRegion() {
		return region;
	}

	public float getVolume() {
		return volume;
	}

	public long getTypeCount() {
		return typeCount;
	}

	public void setTypeCount(long typeCount) {
		this.typeCount = typeCount;
	}

	@Override
	public String toString() {
		return this.getName();
	}

	@Override
	public int compareTo(EveAsset o) {
		return this.getName().compareTo(o.getName());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof EveAsset) {
			return equals((EveAsset) obj);
		}
		return false;
	}

	public boolean equals(EveAsset eveAsset) {
		return this.getName().equals(eveAsset.getName());
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 37 * hash + (this.name != null ? this.name.hashCode() : 0);
		return hash;
	}

	public static String getPriceType() {
		return priceType;
	}

	public static void setPriceType(String priceSource) {
		if (EveAsset.getPriceTypes().contains(priceSource)){
			EveAsset.priceType = priceSource;
		} else {
			EveAsset.priceType = PRICE_MIDPOINT;
		}
		
	}

	public static Vector<String> getPriceTypes(){
		Vector<String> priceSources = new Vector<String>();
		priceSources.add(PRICE_SELL_MAX);
		priceSources.add(PRICE_SELL_AVG);
		priceSources.add(PRICE_SELL_MEDIAN);
		priceSources.add(PRICE_SELL_MIN);
		priceSources.add(PRICE_MIDPOINT);
		priceSources.add(PRICE_BUY_MAX);
		priceSources.add(PRICE_BUY_AVG);
		priceSources.add(PRICE_BUY_MEDIAN);
		priceSources.add(PRICE_BUY_MIN);
		return priceSources;
	}
}
