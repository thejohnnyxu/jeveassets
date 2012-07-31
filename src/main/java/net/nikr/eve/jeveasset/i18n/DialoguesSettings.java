/*
 * Copyright 2009, 2010, 2011, 2012 Contributors (see credits.txt)
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

package net.nikr.eve.jeveasset.i18n;

import java.util.Locale;
import net.nikr.eve.jeveasset.Main;
import uk.me.candle.translations.Bundle;

/**
 *
 * @author Candle
 */
public abstract class DialoguesSettings extends Bundle {
	public static DialoguesSettings get() {
		return Main.getBundleService().get(DialoguesSettings.class);
	}

	public DialoguesSettings(final Locale locale) {
		super(locale);
	}
	// used in AssetsToolSettingsPanel
	public abstract String assets();
	public abstract String showSellOrReprocessColours();
	public abstract String includeSellOrders();
	public abstract String includeBuyOrders();
	public abstract String maximumPurchaseAge();
	public abstract String days();

	// used in GeneralSettingsPanel
	public abstract String general();
	public abstract String searchForNewVersion(String programName);
	public abstract String searchForNewVersionBeta();
	public abstract String enterFilter();
	public abstract String highlightSelectedRow();

	// used in OverviewToolSettingsPanel
	public abstract String overview();
	public abstract String ignoreAuditLogContainers();

	// used in StockpileToolSettingsPanel
	public abstract String stockpile();
	public abstract String stockpileSwitchTab();
	public abstract String stockpileColors();
	public abstract String stockpile0_100();
	public abstract String stockpile0_50();
	public abstract String stockpile50_100();
	public abstract String stockpile100();

	// used in PriceDataSettingsPanel
	public abstract String priceData();
	public abstract String changeSourceWarning();
	public abstract String includeRegions();
	public abstract String includeStations();
	public abstract String includeSystems();
	public abstract String price();
	public abstract String source();
	public abstract String notConfigurable();

	// used in ProxySettingsPanel
	public abstract String proxy();
	public abstract String type();
	public abstract String address();
	public abstract String port();
	public abstract String enable();
	public abstract String apiProxy();

	// used in ReprocessingSettingsPanel
	public abstract String reprocessing();
	public abstract String reprocessingWarning();
	public abstract String stationEquipment();
	public abstract String fiftyPercent();
	public abstract String customPercent();
	public abstract String percentSymbol();
	public abstract String refiningLevel();
	public abstract String refiningEfficiencyLevel();
	public abstract String scrapMetalProcessingLevel();
	public abstract String zero();
	public abstract String one();
	public abstract String two();
	public abstract String three();
	public abstract String four();
	public abstract String five();

	// used in SettingsDialog
	public abstract String settings(String programName);
	public abstract String root();
	public abstract String ok();
	public abstract String apply();
	public abstract String cancel();

	// used in UserItemNameSettingsPanel
	public abstract String names();
	public abstract String name();
	public abstract String namesInstruction();

	// used in UserPriceSettingsPanel
	public abstract String pricePrices();
	public abstract String pricePrice();
	public abstract String priceInstructions();

	// used in WindowSettingsPanel
	public abstract String windowWindow();
	public abstract String windowSaveOnExit();
	public abstract String windowAlwaysOnTop();
	public abstract String windowFixed();
	public abstract String windowWidth();
	public abstract String windowHeight();
	public abstract String windowX();
	public abstract String windowY();
	public abstract String windowMaximised();
	public abstract String windowDefault();
}
