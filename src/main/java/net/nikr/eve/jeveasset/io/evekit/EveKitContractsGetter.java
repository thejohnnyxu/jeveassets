/*
 * Copyright 2009-2019 Contributors (see credits.txt)
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
package net.nikr.eve.jeveasset.io.evekit;

import enterprises.orbital.evekit.client.ApiException;
import enterprises.orbital.evekit.client.ApiResponse;
import enterprises.orbital.evekit.client.model.Contract;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.nikr.eve.jeveasset.data.api.accounts.EveKitAccessMask;
import net.nikr.eve.jeveasset.data.api.accounts.EveKitOwner;
import net.nikr.eve.jeveasset.data.api.my.MyContract;
import net.nikr.eve.jeveasset.data.api.my.MyContractItem;
import net.nikr.eve.jeveasset.data.settings.Settings;
import net.nikr.eve.jeveasset.gui.dialogs.update.UpdateTask;
import net.nikr.eve.jeveasset.io.evekit.AbstractEveKitGetter.EveKitPagesHandler;


public class EveKitContractsGetter extends AbstractEveKitGetter implements EveKitPagesHandler<Contract> {

	private enum Runs {
		IN_PROGRESS, MONTHS, ALL
	}

	private final List<Runs> runs = new ArrayList<Runs>();
	private Runs run;

	public EveKitContractsGetter(UpdateTask updateTask, EveKitOwner owner, boolean first) {
		super(updateTask, owner, true, owner.getContractsNextUpdate(), TaskType.CONTRACTS, first, null);
		runs.add(Runs.ALL);
	}

	public EveKitContractsGetter(UpdateTask updateTask, EveKitOwner owner, Long at) {
		super(updateTask, owner, true, owner.getContractsNextUpdate(), TaskType.CONTRACTS, false, at);
		runs.add(Runs.ALL);
	}

	public EveKitContractsGetter(UpdateTask updateTask, EveKitOwner owner) {
		super(updateTask, owner, false, owner.getContractsNextUpdate(), TaskType.CONTRACTS, false, null);
		if (Settings.get().getEveKitContractsHistory() == 0) {
			runs.add(Runs.ALL);
		} else {
			runs.add(Runs.IN_PROGRESS);
			runs.add(Runs.MONTHS);
		}
	}

	@Override
	protected void update(Long at, boolean first) throws ApiException {
		ArrayList<Contract> data = new ArrayList<Contract>();
		for (Runs r : runs) {
			run = r;
			List<Contract> list = updatePages(this);
			if (list == null) {
				return;
			}
			data.addAll(list);
		}
		Map<MyContract, List<MyContractItem>> map = new HashMap<MyContract, List<MyContractItem>>();
		if (loadCID() != null) { //Old
			map.putAll(owner.getContracts());
		}
		map.putAll(EveKitConverter.toContracts(data, owner)); //New
		owner.setContracts(map); //All
	}

	@Override
	public ApiResponse<List<Contract>> get(String at, Long contid, Integer maxResults) throws ApiException {
		switch (run) {
			case IN_PROGRESS:
				return getCommonApi().getContractsWithHttpInfo(owner.getAccessKey(), owner.getAccessCred(), at, contid, maxResults, false,
						null, null, null, null, null, null, null, null, contractsFilter(), null, null, null, null, null, null, null, null, null, null, null, null, null);
			case MONTHS:
				return getCommonApi().getContractsWithHttpInfo(owner.getAccessKey(), owner.getAccessCred(), at, contid, maxResults, false,
						null, null, null, null, null, null, null, null, null, null, null, null, null, dateFilter(Settings.get().getEveKitContractsHistory()), null, null, null, null, null, null, null, null);
			default: //ALL
				return getCommonApi().getContractsWithHttpInfo(owner.getAccessKey(), owner.getAccessCred(), at, contid, maxResults, false,
						null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
				
		}
	}

	@Override
	public long getCID(Contract obj) {
		return obj.getCid();
	}

	@Override
	public Long getLifeStart(Contract obj) {
		return obj.getLifeStart();
	}

	@Override
	protected boolean haveAccess() {
		return EveKitAccessMask.CONTRACTS.isInMask(owner.getAccessMask());
	}

	@Override
	protected void setNextUpdate(Date date) {
		if (run == Runs.MONTHS || run == Runs.ALL) { //Ignore first update...
			owner.setContractsNextUpdate(date);
		}
	}

	@Override
	public void saveCID(Long cid) {
		if (run == Runs.MONTHS || run == Runs.ALL) { //Ignore first update...
			owner.setContractsCID(cid);
		}
	}

	@Override
	public Long loadCID() {
		if (run == Runs.MONTHS || run == Runs.ALL) { //Ignore first update...
			return owner.getContractsCID();
		} else {
			return null;
		}
	}
}
