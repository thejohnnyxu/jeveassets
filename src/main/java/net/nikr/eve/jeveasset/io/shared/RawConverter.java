/*
 * Copyright 2009-2017 Contributors (see credits.txt)
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
package net.nikr.eve.jeveasset.io.shared;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import net.nikr.eve.jeveasset.data.api.raw.RawAsset;
import net.nikr.eve.jeveasset.data.api.raw.RawContract;
import net.nikr.eve.jeveasset.data.api.raw.RawIndustryJob;
import net.nikr.eve.jeveasset.data.api.raw.RawJournal;
import net.nikr.eve.jeveasset.data.api.raw.RawJournalExtraInfo;
import net.nikr.eve.jeveasset.data.api.raw.RawJournalRefType;
import net.nikr.eve.jeveasset.data.api.raw.RawMarketOrder;
import net.nikr.eve.jeveasset.data.sde.ItemFlag;
import net.nikr.eve.jeveasset.data.sde.MyLocation;
import net.troja.eve.esi.model.CharacterWalletJournalResponse;
import org.joda.time.DateTime;

public class RawConverter {

	private static Map<Integer, RawJournalRefType> journalRefTypes = null;

	public static Long toLong(Number value) {
		if (value != null) {
			return value.longValue();
		} else {
			return null;
		}
	}

	public static Integer toInteger(Number value) {
		if (value != null) {
			return value.intValue();
		} else {
			return null;
		}
	}

	public static Float toFloat(Number value) {
		if (value != null) {
			return value.floatValue();
		} else {
			return null;
		}
	}

	public static Date toDate(OffsetDateTime dateTime) {
		if (dateTime == null) {
			return null;
		} else {
			return Date.from(dateTime.toInstant());
		}
	}

	public static Date toDate(DateTime dateTime) {
		if (dateTime == null) {
			return null;
		} else {
			return dateTime.toDate();
		}
	}

	public static ItemFlag toFlag(net.troja.eve.esi.model.CharacterAssetsResponse.LocationFlagEnum locationFlagEnum) {
		LocationFlag locationFlag = LocationFlag.valueOf(locationFlagEnum.name());
		return ApiIdConverter.getFlag(locationFlag.getID());
	}

	public static ItemFlag toFlag(net.troja.eve.esi.model.CharacterBlueprintsResponse.LocationFlagEnum locationFlagEnum) {
		LocationFlag locationFlag = LocationFlag.valueOf(locationFlagEnum.name());
		return ApiIdConverter.getFlag(locationFlag.getID());
	}

	public static RawAsset.LocationType toAssetLocationType(Long locationID) {
		MyLocation location = ApiIdConverter.getLocation(locationID);
		if (location.isStation()) {
			return RawAsset.LocationType.STATION;
		} else if (location.isSystem()) {
			return RawAsset.LocationType.SOLAR_SYSTEM;
		} else {
			return RawAsset.LocationType.OTHER;
		}
	}

	public static RawContract.ContractAvailability toContractAvailability(String value) {
		switch (value.toLowerCase()) {
			case "private":
				return RawContract.ContractAvailability.PERSONAL;
			case "public":
				return RawContract.ContractAvailability.PUBLIC;
			case "alliance":
				return RawContract.ContractAvailability.ALLIANCE;
			case "corporation":
				return RawContract.ContractAvailability.CORPORATION;
			case "personal":
				return RawContract.ContractAvailability.PERSONAL;
			default:
				throw new RuntimeException("Can't convert: " + value + " to ContractAvailability");
		}
	}

	public static RawContract.ContractAvailability toContractAvailability(com.beimin.eveapi.model.shared.ContractAvailability value) {
		switch (value) {
			case PRIVATE:
				return RawContract.ContractAvailability.PERSONAL;
			case PUBLIC:
				return RawContract.ContractAvailability.PUBLIC;
			default:
				throw new RuntimeException("Can't convert: " + value + " to ContractAvailability");
		}
	}

	public static RawContract.ContractStatus toContractStatus(String value) {
		switch (value.toUpperCase()) {
			case "CANCELLED":
				return RawContract.ContractStatus.CANCELLED;
			case "DELETED":
				return RawContract.ContractStatus.DELETED;
			case "FAILED":
				return RawContract.ContractStatus.FAILED;
			case "FINISHED":
				return RawContract.ContractStatus.FINISHED;
			case "COMPLETED":
				return RawContract.ContractStatus.FINISHED;
			case "FINISHED_CONTRACTOR":
				return RawContract.ContractStatus.FINISHED_CONTRACTOR;
			case "COMPLETEDBYCONTRACTOR":
				return RawContract.ContractStatus.FINISHED_CONTRACTOR;
			case "FINISHED_ISSUER":
				return RawContract.ContractStatus.FINISHED_ISSUER;
			case "COMPLETEDBYISSUER":
				return RawContract.ContractStatus.FINISHED_ISSUER;
			case "IN_PROGRESS":
				return RawContract.ContractStatus.IN_PROGRESS;
			case "INPROGRESS":
				return RawContract.ContractStatus.IN_PROGRESS;
			case "OUTSTANDING":
				return RawContract.ContractStatus.OUTSTANDING;
			case "REJECTED":
				return RawContract.ContractStatus.REJECTED;
			case "REVERSED":
				return RawContract.ContractStatus.REVERSED;
			default:
				throw new RuntimeException("Can't convert: " + value + " to ContractStatus");
		}
	}

	public static RawContract.ContractStatus toContractStatus(com.beimin.eveapi.model.shared.ContractStatus value) {
		switch (value) {
			case OUTSTANDING:
				return RawContract.ContractStatus.OUTSTANDING;
			case DELETED:
				return RawContract.ContractStatus.DELETED;
			case COMPLETED:
				return RawContract.ContractStatus.FINISHED;
			case FAILED:
				return RawContract.ContractStatus.FAILED;
			case COMPLETEDBYISSUER:
				return RawContract.ContractStatus.FINISHED_ISSUER;
			case COMPLETEDBYCONTRACTOR:
				return RawContract.ContractStatus.FINISHED_CONTRACTOR;
			case CANCELLED:
				return RawContract.ContractStatus.CANCELLED;
			case REJECTED:
				return RawContract.ContractStatus.REJECTED;
			case REVERSED:
				return RawContract.ContractStatus.REVERSED;
			case INPROGRESS:
				return RawContract.ContractStatus.IN_PROGRESS;
			default:
				throw new RuntimeException("Can't convert: " + value + " to ContractStatus");
		}

	}

	public static RawContract.ContractType toContractType(String value) {
		if (value == null) {
			return RawContract.ContractType.UNKNOWN;
		} else {
			switch (value.toLowerCase()) {
				case "item_exchange":
					return RawContract.ContractType.ITEM_EXCHANGE;
				case "itemexchange":
					return RawContract.ContractType.ITEM_EXCHANGE;
				case "courier":
					return RawContract.ContractType.COURIER;
				case "loan":
					return RawContract.ContractType.LOAN;
				case "auction":
					return RawContract.ContractType.AUCTION;
				default:
					return RawContract.ContractType.UNKNOWN;
			}
		}
	}

	public static RawContract.ContractType toContractType(com.beimin.eveapi.model.shared.ContractType value) {
		if (value == null) {
			return RawContract.ContractType.UNKNOWN;
		} else {
			switch (value) {
				case ITEMEXCHANGE:
					return RawContract.ContractType.ITEM_EXCHANGE;
				case COURIER:
					return RawContract.ContractType.COURIER;
				case LOAN:
					return RawContract.ContractType.LOAN;
				case AUCTION:
					return RawContract.ContractType.AUCTION;
				default:
					return RawContract.ContractType.UNKNOWN;
			}
		}
	}

	public static RawIndustryJob.IndustryJobStatus toIndustryJobStatus(int value) {
		switch (value) {
			case 1:
				return RawIndustryJob.IndustryJobStatus.ACTIVE;
			case 2:
				return RawIndustryJob.IndustryJobStatus.PAUSED;
			case 3:
				return RawIndustryJob.IndustryJobStatus.READY;
			case 101:
				return RawIndustryJob.IndustryJobStatus.DELIVERED;
			case 102:
				return RawIndustryJob.IndustryJobStatus.CANCELLED;
			case 103:
				return RawIndustryJob.IndustryJobStatus.REVERTED;
			default:
				throw new RuntimeException("Can't convert: " + value + " to IndustryJobStatus");
		}
	}

	public static int fromIndustryJobStatus(RawIndustryJob.IndustryJobStatus value) {
		switch (value) {
			case ACTIVE:
				return 1;
			case PAUSED:
				return 2;
			case READY:
				return 3;
			case DELIVERED:
				return 101;
			case CANCELLED:
				return 102;
			case REVERTED:
				return 103;
			default:
				throw new RuntimeException("Can't convert: " + value + " to IndustryJobStatus");
		}
	}

	public static RawJournalRefType toJournalRefType(int value) {
		createCache();
		return journalRefTypes.get(value);
	}

	public static RawJournalRefType toJournalRefType(CharacterWalletJournalResponse.RefTypeEnum value) {
		switch (value) {
			case ALLIANCE_MAINTENANCE_FEE:
				return RawJournalRefType.ALLIANCE_MAINTAINANCE_FEE;			//OK
			case BOUNTY_PRIZES:
				return RawJournalRefType.BOUNTY_PRIZES;						//OK
			case BOUNTY_PRIZE_HISTORICAL:
				return RawJournalRefType.BOUNTY_PRIZE;						//??
			case BROKER_FEE:
				return RawJournalRefType.BROKERS_FEE;						//OK
			case CONTRACT:
				return RawJournalRefType.CONTRACT_BROKERS_FEE;				//FAIL
			case CORPORATE_REWARD_PAYOUT:
				return RawJournalRefType.CORPORATE_REWARD_PAYOUT;			//OK
			case CORP_ACCOUNT_WITHDRAWAL:
				return RawJournalRefType.CORPORATION_ACCOUNT_WITHDRAWAL;	//OK
			case CSPA:
				return RawJournalRefType.CSPA;								//OK
			case CUSTOMS_OFFICE_EXPORT_DUTY:
				return RawJournalRefType.PLANETARY_EXPORT_TAX;				//??
			case CUSTOMS_OFFICE_IMPORT_DUTY:
				return RawJournalRefType.PLANETARY_IMPORT_TAX;				//??
			case INDUSTRY_FACILITY_TAX:
				return RawJournalRefType.INDUSTRY_JOB_TAX;					//??
			case INSURANCE:
				return RawJournalRefType.INSURANCE;							//OK
			case JUMP_CLONE_ACTIVATION_FEE:
				return RawJournalRefType.JUMP_CLONE_ACTIVATION_FEE;			//OK
			case JUMP_CLONE_INSTALLATION_FEE:
				return RawJournalRefType.JUMP_CLONE_INSTALLATION_FEE;		//OK
			case LOGO_CHANGE_FEE:
				return RawJournalRefType.CORPORATION_LOGO_CHANGE_COST;		//??
			case MANUFACTURING:
				return RawJournalRefType.MANUFACTURING;						//OK
			case MARKET_ESCROW:
				return RawJournalRefType.MARKET_ESCROW;						//OK
			case MARKET_TRANSACTION:
				return RawJournalRefType.MARKET_TRANSACTION;				//OK
			case MEDAL_CREATION_FEE:
				return RawJournalRefType.MEDAL_CREATION;					//??
			case MEDAL_ISSUING_FEE:
				return RawJournalRefType.MEDAL_ISSUED;						//??
			case MISSION_REWARD:
				return RawJournalRefType.MISSION_REWARD;					//??
			case MISSION_REWARD_BONUS:
				return RawJournalRefType.AGENT_MISSION_TIME_BONUS_REWARD;	//??
			case OFFICE_RENTAL_FEE:
				return RawJournalRefType.OFFICE_RENTAL_FEE;					//OK
			case PLAYER_DONATION:
				return RawJournalRefType.PLAYER_DONATION;					//OK
			case PLAYER_TRADING:
				return RawJournalRefType.PLAYER_TRADING;					//OK
			case PROJECT_DISCOVERY_REWARD:
				return RawJournalRefType.PROJECT_DISCOVERY_REWARD;			//OK
			case REPROCESSING_FEE:
				return RawJournalRefType.REPROCESSING_TAX;					//??
			case SALES_TAX:
				return RawJournalRefType.TRANSACTION_TAX;					//??
			case UNKNOWN:
				return RawJournalRefType.UNDEFINED;							//??
			default:
				return RawJournalRefType.UNDEFINED;							//??
		}
	}

	public static RawJournal.JournalPartyType toJournalPartyType(Integer value) {
		if (value == null) {
			return null;
		} else if (value == 2) {
			return RawJournal.JournalPartyType.CORPORATION;
		} else if (value >= 1373 && value <= 1386) {
			return RawJournal.JournalPartyType.CHARACTER;
		} else if (value == 16159) {
			return RawJournal.JournalPartyType.ALLIANCE;
		} else {
			return RawJournal.JournalPartyType.FACTION;
		}
	}

	public static RawJournal.JournalPartyType toJournalPartyType(CharacterWalletJournalResponse.FirstPartyTypeEnum value) {
		if (value == null) {
			return null;
		} else {
			return RawJournal.JournalPartyType.valueOf(value.name());
		}
	}

	public static RawJournal.JournalPartyType toJournalPartyType(CharacterWalletJournalResponse.SecondPartyTypeEnum value) {
		if (value == null) {
			return null;
		} else {
			return RawJournal.JournalPartyType.valueOf(value.name());
		}
	}

	public static RawMarketOrder.MarketOrderRange toMarketOrderRange(int value) {
		switch (value) {
			case -1:
				return RawMarketOrder.MarketOrderRange.STATION;
			case 0:
				return RawMarketOrder.MarketOrderRange.SOLARSYSTEM;
			case 1:
				return RawMarketOrder.MarketOrderRange._1;
			case 2:
				return RawMarketOrder.MarketOrderRange._2;
			case 3:
				return RawMarketOrder.MarketOrderRange._3;
			case 4:
				return RawMarketOrder.MarketOrderRange._4;
			case 5:
				return RawMarketOrder.MarketOrderRange._5;
			case 10:
				return RawMarketOrder.MarketOrderRange._10;
			case 20:
				return RawMarketOrder.MarketOrderRange._20;
			case 30:
				return RawMarketOrder.MarketOrderRange._30;
			case 40:
				return RawMarketOrder.MarketOrderRange._40;
			case 32767:
				return RawMarketOrder.MarketOrderRange.REGION;
			default:
				throw new RuntimeException("Can't convert: " + value + " to MarketOrderRange");
		}
	}

	public static Long fromRawJournalExtraInfoArgID(RawJournalExtraInfo journalExtraInfo) {
		if (journalExtraInfo.getAllianceId() != null) {
			return RawConverter.toLong(journalExtraInfo.getAllianceId());
		} else if (journalExtraInfo.getCharacterId() != null) {
			return RawConverter.toLong(journalExtraInfo.getCharacterId());
		} else if (journalExtraInfo.getLocationId() != null) {
			return RawConverter.toLong(journalExtraInfo.getLocationId());
		} else if (journalExtraInfo.getNpcId() != null) {
			return RawConverter.toLong(journalExtraInfo.getNpcId());
		} else if (journalExtraInfo.getPlanetId() != null) {
			return RawConverter.toLong(journalExtraInfo.getPlanetId());
		} else if (journalExtraInfo.getSystemId() != null) {
			return RawConverter.toLong(journalExtraInfo.getSystemId());
		} else if (journalExtraInfo.getCorporationId() != null) {
			return RawConverter.toLong(journalExtraInfo.getCorporationId());
		} else {
			return null;
		}
	}

	public static String fromRawJournalExtraInfoArgName(RawJournalExtraInfo journalExtraInfo) {
		if (journalExtraInfo.getCharacterId() != null) {
			return journalExtraInfo.getCharacterId().toString();
		} else if (journalExtraInfo.getContractId() != null) {
			return journalExtraInfo.getContractId().toString();
		} else if (journalExtraInfo.getDestroyedShipTypeId() != null) {
			return journalExtraInfo.getDestroyedShipTypeId().toString();
		} else if (journalExtraInfo.getJobId() != null) {
			return journalExtraInfo.getJobId().toString();
		} else if (journalExtraInfo.getNpcName() != null) {
			return journalExtraInfo.getNpcName();
		} else if (journalExtraInfo.getSystemId() != null) {
			return journalExtraInfo.getSystemId().toString();
		} else if (journalExtraInfo.getTransactionId() != null) {
			return journalExtraInfo.getTransactionId().toString();
		} else {
			return null;
		}
	}

	public static int fromMarketOrderRange(RawMarketOrder.MarketOrderRange value) {
		switch (value) {
			case STATION:
				return -1;
			case SOLARSYSTEM:
				return 0;
			case _1:
				return 1;
			case _2:
				return 2;
			case _3:
				return 3;
			case _4:
				return 4;
			case _5:
				return 5;
			case _10:
				return 10;
			case _20:
				return 20;
			case _30:
				return 30;
			case _40:
				return 40;
			case REGION:
				return 32767;
			default:
				throw new RuntimeException("Can't convert: " + value + " to MarketOrderRange");
		}
	}

	public static RawMarketOrder.MarketOrderState toMarketOrderState(int value) {
		switch (value) {
			case 0:
				return RawMarketOrder.MarketOrderState.OPEN;
			case 1:
				return RawMarketOrder.MarketOrderState.CLOSED;
			case 2:
				return RawMarketOrder.MarketOrderState.EXPIRED;
			case 3:
				return RawMarketOrder.MarketOrderState.CANCELLED;
			case 4:
				return RawMarketOrder.MarketOrderState.PENDING;
			case 5:
				return RawMarketOrder.MarketOrderState.CHARACTER_DELETED;
			default:
				throw new RuntimeException("Can't convert: " + value + " to MarketOrderState");
		}
	}

	public static int fromMarketOrderState(RawMarketOrder.MarketOrderState value) {
		switch (value) {
			case OPEN:
				return 0;
			case CLOSED:
				return 1;
			case EXPIRED:
				return 2;
			case CANCELLED:
				return 3;
			case PENDING:
				return 4;
			case CHARACTER_DELETED:
				return 5;
			default:
				throw new RuntimeException("Can't convert: " + value + " to MarketOrderState");
		}
	}

	public static int fromMarketOrderIsBuyOrder(Boolean value) {
		return value ? 1 : 0;
	}

	public static Boolean toTransactionIsBuy(String value) {
		return value.toLowerCase().equals("buy");
	}

	public static Boolean toTransactionIsPersonal(String value) {
		return value.toLowerCase().equals("personal");
	}

	public static String fromTransactionIsBuy(Boolean value) {
		if (value) {
			return "buy";
		} else {
			return "sell";
		}
	}

	public static String fromTransactionIsPersonal(Boolean value) {
		if (value) {
			return "personal";
		} else {
			return "corporate";
		}
	}

	private static void createCache() {
		if (journalRefTypes == null) {
			journalRefTypes = new HashMap<Integer, RawJournalRefType>();
			for (RawJournalRefType journalRefType : RawJournalRefType.values()) {
				journalRefTypes.put(journalRefType.getID(), journalRefType);
			}

		}
	}

	public static int toAssetQuantity(int quantity, Integer rawQuantity) {
		if (rawQuantity != null && rawQuantity < 0) {
			return rawQuantity;
		} else {
			return quantity;
		}
	}

	public static Integer fromJournalPartyType(RawJournal.JournalPartyType value) {
		if (value == null) {
			return null;
		} else {
			switch (value) {
				case ALLIANCE:
					return 16159;
				case CHARACTER:
					return 1373;
				case CORPORATION:
					return 2;
				case FACTION:
					return 1;
				default:
					return 0;
			}
		}
	}

	public enum LocationFlag {
		AUTOFIT("AutoFit", 0),
		MODULE("Module", 0),
		CORPSEBAY("CorpseBay", 0),
		HANGARALL("HangarAll", 0),
		SUBSYSTEMBAY("SubSystemBay", 0), //177
		WARDROBE("Wardrobe", 3),
		HANGAR("Hangar", 4),
		CARGO("Cargo", 5),
		LOSLOT0("LoSlot0", 11),
		LOSLOT1("LoSlot1", 12),
		LOSLOT2("LoSlot2", 13),
		LOSLOT3("LoSlot3", 14),
		LOSLOT4("LoSlot4", 15),
		LOSLOT5("LoSlot5", 16),
		LOSLOT6("LoSlot6", 17),
		LOSLOT7("LoSlot7", 18),
		MEDSLOT0("MedSlot0", 19),
		MEDSLOT1("MedSlot1", 20),
		MEDSLOT2("MedSlot2", 21),
		MEDSLOT3("MedSlot3", 22),
		MEDSLOT4("MedSlot4", 23),
		MEDSLOT5("MedSlot5", 24),
		MEDSLOT6("MedSlot6", 25),
		MEDSLOT7("MedSlot7", 26),
		HISLOT0("HiSlot0", 27),
		HISLOT1("HiSlot1", 28),
		HISLOT2("HiSlot2", 29),
		HISLOT3("HiSlot3", 30),
		HISLOT4("HiSlot4", 31),
		HISLOT5("HiSlot5", 32),
		HISLOT6("HiSlot6", 33),
		HISLOT7("HiSlot7", 34),
		ASSETSAFETY("AssetSafety", 36),
		LOCKED("Locked", 63),
		UNLOCKED("Unlocked", 64),
		DRONEBAY("DroneBay", 87),
		IMPLANT("Implant", 89),
		SHIPHANGAR("ShipHangar", 90),
		RIGSLOT0("RigSlot0", 92),
		RIGSLOT1("RigSlot1", 93),
		RIGSLOT2("RigSlot2", 94),
		RIGSLOT3("RigSlot3", 95),
		RIGSLOT4("RigSlot4", 96),
		RIGSLOT5("RigSlot5", 97),
		RIGSLOT6("RigSlot6", 98),
		RIGSLOT7("RigSlot7", 99),
		SUBSYSTEMSLOT0("SubSystemSlot0", 125),
		SUBSYSTEMSLOT1("SubSystemSlot1", 126),
		SUBSYSTEMSLOT2("SubSystemSlot2", 127),
		SUBSYSTEMSLOT3("SubSystemSlot3", 128),
		SUBSYSTEMSLOT4("SubSystemSlot4", 129),
		SUBSYSTEMSLOT5("SubSystemSlot5", 130),
		SUBSYSTEMSLOT6("SubSystemSlot6", 131),
		SUBSYSTEMSLOT7("SubSystemSlot7", 132),
		SPECIALIZEDFUELBAY("SpecializedFuelBay", 133),
		SPECIALIZEDOREHOLD("SpecializedOreHold", 134),
		SPECIALIZEDGASHOLD("SpecializedGasHold", 135),
		SPECIALIZEDMINERALHOLD("SpecializedMineralHold", 136),
		SPECIALIZEDSALVAGEHOLD("SpecializedSalvageHold", 137),
		SPECIALIZEDSHIPHOLD("SpecializedShipHold", 138),
		SPECIALIZEDSMALLSHIPHOLD("SpecializedSmallShipHold", 139),
		SPECIALIZEDMEDIUMSHIPHOLD("SpecializedMediumShipHold", 140),
		SPECIALIZEDLARGESHIPHOLD("SpecializedLargeShipHold", 141),
		SPECIALIZEDINDUSTRIALSHIPHOLD("SpecializedIndustrialShipHold", 142),
		SPECIALIZEDAMMOHOLD("SpecializedAmmoHold", 143),
		SPECIALIZEDCOMMANDCENTERHOLD("SpecializedCommandCenterHold", 148),
		SPECIALIZEDPLANETARYCOMMODITIESHOLD("SpecializedPlanetaryCommoditiesHold", 149),
		SPECIALIZEDMATERIALBAY("SpecializedMaterialBay", 151),
		QUAFEBAY("QuafeBay", 154),
		FLEETHANGAR("FleetHangar", 155),
		HIDDENMODIFIERS("HiddenModifiers", 156),
		FIGHTERBAY("FighterBay", 158),
		FIGHTERTUBE0("FighterTube0", 159),
		FIGHTERTUBE1("FighterTube1", 160),
		FIGHTERTUBE2("FighterTube2", 161),
		FIGHTERTUBE3("FighterTube3", 162),
		FIGHTERTUBE4("FighterTube4", 163),
		DELIVERIES("Deliveries", 173);

		private final String value;
		private final int id;
		private final ItemFlag itemFlag;

		LocationFlag(String value, int id) {
			this.value = value;
			this.id = id;
			this.itemFlag = null;
		}

		public int getID() {
			return id;
		}

		@Override
		public String toString() {
			return String.valueOf(value);
		}
	}
}