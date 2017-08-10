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
package net.nikr.eve.jeveasset.data.api.my;

import java.util.Objects;
import net.nikr.eve.jeveasset.data.api.accounts.OwnerType;
import net.nikr.eve.jeveasset.data.api.raw.RawJournal;
import net.nikr.eve.jeveasset.data.api.raw.RawJournalRefType;

public class MyJournal extends RawJournal implements Comparable<MyJournal> {

	private final String corp = "(Corporation)";
	private final OwnerType owner;
	private String firstPartyName = null;
	private String secondPartyName = null;

	public MyJournal(RawJournal rawJournal, OwnerType owner) {
		super(rawJournal);
		this.owner = owner;
	}

	public int getAccountKeyFormated() {
		return getAccountKey() - 999;
	}

	public String getOwnerName() {
		return owner.getOwnerName();
	}

	public String getRefTypeFormated() {
		RawJournalRefType refType = getRefType();
		if (refType != null) {
			return capitalizeAll(refType.name().replace("_CORP_", corp).replace('_', ' '));
		} else {
			return "";
		}
	}

	public String getOwnerName1() {
		return firstPartyName;
	}

	public void setFirstPartyName(String firstPartyName) {
		this.firstPartyName = firstPartyName;
	}

	public String getOwnerName2() {
		return secondPartyName;
	}

	public void setSecondPartyName(String secondPartyName) {
		this.secondPartyName = secondPartyName;
	}

	private String capitalize(String s) {
		if (s.length() == 0) {
			return s;
		}
		if (s.equals(corp)) {
			return s;
		}
		return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
	}

	private String capitalizeAll(String in) {
		String[] words = in.split("\\s");
		StringBuilder builder = new StringBuilder();
		for (String word : words) {
			if (builder.length() > 0) {
				builder.append(' ');
			}

			builder.append(capitalize(word));
		}
		return builder.toString();
	}

	@Override
	public int compareTo(MyJournal o) {
		int compared = o.getDate().compareTo(this.getDate());
		if (compared != 0) {
			return compared;
		} else {
			return Double.compare(o.getAmount(), this.getAmount());
		}
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 41 * hash + Objects.hashCode(this.getRefID());
		hash = 41 * hash + Objects.hashCode(this.getAccountKey());
		hash = 41 * hash + Objects.hashCode(owner.getOwnerID());
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final MyJournal other = (MyJournal) obj;
		if (!Objects.equals(this.owner.getOwnerID(), other.owner.getOwnerID())) {
			return false;
		}
		if (!Objects.equals(this.getRefID(), other.getRefID())) {
			return false;
		}
		return Objects.equals(this.getAccountKey(), other.getAccountKey());
	}
}