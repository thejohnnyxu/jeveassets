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
package net.nikr.eve.jeveasset.io.esi;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.nikr.eve.jeveasset.TestUtil;
import net.nikr.eve.jeveasset.data.api.my.MyAccountBalance;
import net.nikr.eve.jeveasset.data.api.my.MyAsset;
import net.nikr.eve.jeveasset.data.api.my.MyContract;
import net.nikr.eve.jeveasset.data.api.my.MyContractItem;
import net.nikr.eve.jeveasset.data.api.my.MyIndustryJob;
import net.nikr.eve.jeveasset.data.api.my.MyJournal;
import net.nikr.eve.jeveasset.data.api.my.MyMarketOrder;
import net.nikr.eve.jeveasset.data.api.my.MyTransaction;
import net.nikr.eve.jeveasset.data.api.raw.RawBlueprint;
import net.nikr.eve.jeveasset.io.shared.ConverterTestOptions;
import net.nikr.eve.jeveasset.io.shared.ConverterTestOptionsGetter;
import net.nikr.eve.jeveasset.io.shared.ConverterTestUtil;
import net.troja.eve.esi.model.CharacterAssetsResponse;
import net.troja.eve.esi.model.CharacterBlueprintsResponse;
import net.troja.eve.esi.model.CharacterContractsItemsResponse;
import net.troja.eve.esi.model.CharacterContractsResponse;
import net.troja.eve.esi.model.CharacterIndustryJobsResponse;
import net.troja.eve.esi.model.CharacterOrdersResponse;
import net.troja.eve.esi.model.CharacterWalletJournalResponse;
import net.troja.eve.esi.model.CharacterWalletTransactionsResponse;
import org.junit.Test;


public class EsiConverterTest extends TestUtil {

	@Test
	public void testToAccountBalance() {
		testToAccountBalance(null);
	}

	public void testToAccountBalance(Class<?> esi) {
		for (ConverterTestOptions options : ConverterTestOptionsGetter.getConverterOptions()) {
			List<MyAccountBalance> accountBalances = EsiConverter.toAccountBalance(options.getFloat(), ConverterTestUtil.getEsiOwner(options), options.getInteger());
			ConverterTestUtil.testValues(accountBalances.get(0), options, esi);
		}
	}

	@Test
	public void testToAssets() {
		testToAssets(null);
	}

	@Test
	public void testToAssetsOptional() {
		testToAssets(CharacterAssetsResponse.class);
	}

	public void testToAssets(Class<?> esi) {
		for (ConverterTestOptions options : ConverterTestOptionsGetter.getConverterOptions()) {
			List<CharacterAssetsResponse> assetsResponses = new ArrayList<CharacterAssetsResponse>();

			CharacterAssetsResponse rootAssetsResponse = new CharacterAssetsResponse();
			assetsResponses.add(rootAssetsResponse);
			ConverterTestUtil.setValues(rootAssetsResponse, options, esi);

			CharacterAssetsResponse childAssetsResponse = new CharacterAssetsResponse();
			assetsResponses.add(childAssetsResponse);
			ConverterTestUtil.setValues(childAssetsResponse, options, esi);
			childAssetsResponse.setItemId(childAssetsResponse.getItemId() + 1);
			childAssetsResponse.setLocationId(rootAssetsResponse.getItemId());

			List<MyAsset> assets = EsiConverter.toAssets(assetsResponses, ConverterTestUtil.getEsiOwner(options));
			if (rootAssetsResponse.getLocationFlag() != CharacterAssetsResponse.LocationFlagEnum.IMPLANT) {
				assertEquals("List empty @" + options.getIndex(), 1, assets.size());
				ConverterTestUtil.testValues(assets.get(0), options, esi);

				assertEquals("List empty @" + options.getIndex(), 1, assets.get(0).getAssets().size());
				MyAsset childAsset = assets.get(0).getAssets().get(0);
				childAsset.setItemID(childAsset.getItemID() - 1);
				ConverterTestUtil.testValues(childAsset, options, esi);
			} else {
				assertEquals(assets.size(), 0);
			}
		}
	}

	@Test
	public void testToBlueprints() {
		testBlueprints(null);
	}

	@Test
	public void testToBlueprintsOptional() {
		testBlueprints(CharacterBlueprintsResponse.class);
	}

	private void testBlueprints(Class<?> esi) {
		for (ConverterTestOptions options : ConverterTestOptionsGetter.getConverterOptions()) {
			CharacterBlueprintsResponse blueprintsResponse = new CharacterBlueprintsResponse();
			ConverterTestUtil.setValues(blueprintsResponse, options, esi);
			Map<Long, RawBlueprint> blueprints = EsiConverter.toBlueprints(Collections.singletonList(blueprintsResponse));
			ConverterTestUtil.testValues(blueprints.values().iterator().next(), options, esi);
		}
	}

	@Test
	public void testToIndustryJobs() {
		testToIndustryJobs(null);
	}

	@Test
	public void testToIndustryJobsOptional() {
		testToIndustryJobs(CharacterIndustryJobsResponse.class);
	}

	private void testToIndustryJobs(Class<?> esi) {
		for (ConverterTestOptions options : ConverterTestOptionsGetter.getConverterOptions()) {
			CharacterIndustryJobsResponse industryJobsResponse = new CharacterIndustryJobsResponse();
			ConverterTestUtil.setValues(industryJobsResponse, options, esi);
			List<MyIndustryJob> industryJobs = EsiConverter.toIndustryJobs(Collections.singletonList(industryJobsResponse), ConverterTestUtil.getEsiOwner(options));
			ConverterTestUtil.testValues(industryJobs.get(0), options, esi);
		}
	}

	@Test
	public void testToJournals() {
		testToJournals(null);
	}

	@Test
	public void testToJournalsOptional() {
		testToJournals(CharacterWalletJournalResponse.class);
	}

	private void testToJournals(Class<?> esi) {
		for (ConverterTestOptions options : ConverterTestOptionsGetter.getConverterOptions()) {
			CharacterWalletJournalResponse journalResponse = new CharacterWalletJournalResponse();
			ConverterTestUtil.setValues(journalResponse, options, esi);
			Set<MyJournal> journals = EsiConverter.toJournals(Collections.singletonList(journalResponse), ConverterTestUtil.getEsiOwner(options), options.getInteger(), false);
			ConverterTestUtil.testValues(journals.iterator().next(), options, esi);
		}
	}

	@Test
	public void testToContracts() {
		testToContracts(null);
	}

	@Test
	public void testToContractsOptional() {
		testToContracts(CharacterContractsResponse.class);
	}

	public void testToContracts(Class<?> esi) {
		for (ConverterTestOptions options : ConverterTestOptionsGetter.getConverterOptions()) {
			CharacterContractsResponse contractsResponse = new CharacterContractsResponse();
			ConverterTestUtil.setValues(contractsResponse, options, esi);
			Map<MyContract, List<MyContractItem>> contracts = EsiConverter.toContracts(Collections.singletonList(contractsResponse), ConverterTestUtil.getEsiOwner(options));
			ConverterTestUtil.testValues(contracts.keySet().iterator().next(), options, esi);
		}
	}

	@Test
	public void testToContractItems() {
		testToContractItems(null);
	}

	@Test
	public void testToContractItemsOptional() {
		testToContractItems(CharacterContractsItemsResponse.class);
	}

	public void testToContractItems(Class<?> esi) {
		for (ConverterTestOptions options : ConverterTestOptionsGetter.getConverterOptions()) {
			CharacterContractsItemsResponse contractsItemsResponse = new CharacterContractsItemsResponse();
			ConverterTestUtil.setValues(contractsItemsResponse, options, esi);
			Map<MyContract, List<MyContractItem>> contractItems = EsiConverter.toContractItems(ConverterTestUtil.getMyContract(false, true, options), Collections.singletonList(contractsItemsResponse), ConverterTestUtil.getEsiOwner(options));
			ConverterTestUtil.testValues(contractItems.values().iterator().next().get(0), options, esi);
		}
	}

	@Test
	public void testToMarketOrders() {
		testToMarketOrders(null);
	}

	@Test
	public void testToMarketOrdersOptional() {
		testToMarketOrders(CharacterOrdersResponse.class);
	}

	public void testToMarketOrders(Class<?> esi) {
		for (ConverterTestOptions options : ConverterTestOptionsGetter.getConverterOptions()) {
			CharacterOrdersResponse ordersResponse = new CharacterOrdersResponse();
			ConverterTestUtil.setValues(ordersResponse, options, esi);
			Set<MyMarketOrder> marketOrders = EsiConverter.toMarketOrders(Collections.singletonList(ordersResponse), ConverterTestUtil.getEsiOwner(options), false);
			ConverterTestUtil.testValues(marketOrders.iterator().next(), options, esi);
		}
	}

	@Test
	public void testToTransaction() {
		testToTransaction(null);
	}

	@Test
	public void testToTransactionOptional() {
		testToTransaction(CharacterWalletTransactionsResponse.class);
	}

	public void testToTransaction(Class<?> esi) {
		for (ConverterTestOptions options : ConverterTestOptionsGetter.getConverterOptions()) {
			CharacterWalletTransactionsResponse transactionsResponse = new CharacterWalletTransactionsResponse();
			ConverterTestUtil.setValues(transactionsResponse, options, esi);
			Set<MyTransaction> transactions = EsiConverter.toTransaction(Collections.singletonList(transactionsResponse), ConverterTestUtil.getEsiOwner(options), options.getInteger(), false);
			ConverterTestUtil.testValues(transactions.iterator().next(), options, esi);
		}
	}
}
