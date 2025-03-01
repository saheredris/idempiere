/**********************************************************************
* This file is part of iDempiere ERP Open Source                      *
* http://www.idempiere.org                                            *
*                                                                     *
* Copyright (C) Contributors                                          *
*                                                                     *
* This program is free software; you can redistribute it and/or       *
* modify it under the terms of the GNU General Public License         *
* as published by the Free Software Foundation; either version 2      *
* of the License, or (at your option) any later version.              *
*                                                                     *
* This program is distributed in the hope that it will be useful,     *
* but WITHOUT ANY WARRANTY; without even the implied warranty of      *
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the        *
* GNU General Public License for more details.                        *
*                                                                     *
* You should have received a copy of the GNU General Public License   *
* along with this program; if not, write to the Free Software         *
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,          *
* MA 02110-1301, USA.                                                 *
*                                                                     *
* Contributors:                                                       *
* - Igor Pojzl, Cloudempiere                                          *
* - Peter Takacs, Cloudempiere                                        *
**********************************************************************/
package org.compiere.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.util.Env;
import org.compiere.util.Util;
import org.idempiere.cache.ImmutableIntPOCache;
import org.idempiere.cache.ImmutablePOSupport;

/**
 * Process drill rule model
 * @author Igor Pojzl, Cloudempiere
 * @author Peter Takacs, Cloudempiere
 */
public class MProcessDrillRule extends X_AD_Process_DrillRule implements ImmutablePOSupport {

	/**	Process Parameter			*/
	private MProcessDrillRulePara[] m_parameter = null;

	/**
	 * generated serial id
	 */
	private static final long serialVersionUID = -6543978637922025586L;

	/**	Process Drill Rule Cache				*/
	private static ImmutableIntPOCache<Integer,MProcessDrillRule>	s_cache = new ImmutableIntPOCache<Integer,MProcessDrillRule>(Table_Name, 20);

	/**
	 * Get MProcessDrillRule Cached(Immutable)
	 * @param ctx
	 * @param AD_Process_DrillRule_ID
	 * @return MProcessDrillRule or null
	 */
	public static MProcessDrillRule get(Properties ctx, int AD_Process_DrillRule_ID) {
		return get(ctx, AD_Process_DrillRule_ID, null);
	}

	/**
	 * Get MProcessDrillRule Cached(Immutable)
	 * @param ctx
	 * @param AD_Process_DrillRule_ID
	 * @param trxName
	 * @return MProcessDrillRule or null
	 */
	public static MProcessDrillRule get(Properties ctx, int AD_Process_DrillRule_ID, String trxName) {
		Integer ii = Integer.valueOf(AD_Process_DrillRule_ID);
		MProcessDrillRule retValue = s_cache.get(ctx, ii, e -> new MProcessDrillRule(ctx, e));
		if (retValue != null)
			return retValue;
		retValue = new MProcessDrillRule (ctx, AD_Process_DrillRule_ID, (String)null);
		if (retValue.get_ID () == AD_Process_DrillRule_ID)
		{
			s_cache.put (AD_Process_DrillRule_ID, retValue, e -> new MProcessDrillRule(Env.getCtx(), e));
			return retValue;
		}
		return null;
	}


    /**
     * UUID based Constructor
     * @param ctx  Context
     * @param AD_Process_DrillRule_UU  UUID key
     * @param trxName Transaction
     */
    public MProcessDrillRule(Properties ctx, String AD_Process_DrillRule_UU, String trxName) {
        super(ctx, AD_Process_DrillRule_UU, trxName);
    }

    /**
     * @param ctx
     * @param AD_Process_DrillRule_ID
     * @param trxName
     */
	public MProcessDrillRule(Properties ctx, int AD_Process_DrillRule_ID, String trxName) {
		super(ctx, AD_Process_DrillRule_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param AD_Process_DrillRule_ID
	 * @param trxName
	 * @param virtualColumns
	 */
	public MProcessDrillRule(Properties ctx, int AD_Process_DrillRule_ID, String trxName, String[] virtualColumns) {
		super(ctx, AD_Process_DrillRule_ID, trxName, virtualColumns);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MProcessDrillRule(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/**
	 * Copy constructor
	 * @param copy
	 */
	public MProcessDrillRule(MProcessDrillRule copy)
	{
		this(Env.getCtx(), copy);
	}

	/**
	 * Copy constructor
	 * @param ctx
	 * @param copy
	 */
	public MProcessDrillRule(Properties ctx, MProcessDrillRule copy)
	{
		this(ctx, copy, (String) null);
	}

	/**
	 * Copy constructor
	 * @param ctx
	 * @param copy
	 * @param trxName
	 */
	public MProcessDrillRule(Properties ctx, MProcessDrillRule copy, String trxName)
	{
		this(ctx, 0, trxName);
		copyPO(copy);
	}

	/**
	 * Get array of MProcessDrillRule by Table
	 * @param ctx
	 * @param AD_Table_ID
	 * @param trxName
	 * @return array of MProcessDrillRule
	 */
	public static MProcessDrillRule[] getByTable(Properties ctx, int AD_Table_ID, String trxName) {

		String whereClause = " AD_Table_ID = ? AND IsValid = 'Y' AND " + MProcessDrillRule.Table_Name + "." + MProcessDrillRule.COLUMNNAME_AD_Client_ID + " IN (0,?)";
		List<MProcessDrillRule> processDrillRules = new Query(ctx, MProcessDrillRule.Table_Name, whereClause, trxName)
				.setParameters(AD_Table_ID, Env.getAD_Client_ID(ctx))
				.setOnlyActiveRecords(true)
				.list();

		return processDrillRules.toArray(new MProcessDrillRule[processDrillRules.size()]);
	}

	/**
	 * Get array of MProcessDrillRule by Column Name
	 * @param ctx
	 * @param columnName
	 * @param trxName
	 * @return array of MProcessDrillRule
	 */
	public static MProcessDrillRule[] getByColumnName(Properties ctx, String columnName, String trxName) {

		String whereClause = " IsValid = 'Y' AND " + MProcessDrillRule.Table_Name + "." + MProcessDrillRule.COLUMNNAME_AD_Client_ID + " IN (0,?)";
		List<MProcessDrillRule> processDrillRules = new Query(ctx, MProcessDrillRule.Table_Name, whereClause, trxName)
				.addJoinClause(" INNER JOIN AD_Process_Para pp ON "
								+ MProcessDrillRule.Table_Name + "." + MProcessDrillRule.COLUMNNAME_AD_Process_Para_ID + " = pp." + MProcessPara.COLUMNNAME_AD_Process_Para_ID
								+ " AND " + MProcessPara.COLUMNNAME_ColumnName + " = ?")
				.setParameters(columnName, Env.getAD_Client_ID(ctx))
				.setOnlyActiveRecords(true)
				.list();

		return processDrillRules.toArray(new MProcessDrillRule[processDrillRules.size()]);
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {

		if(newRecord || is_ValueChanged(MProcessDrillRule.COLUMNNAME_AD_Process_ID)) {
			MProcess process = MProcess.get(getAD_Process_ID());
			if(process != null && process.getAD_ReportView_ID() > 0) {
				MReportView reportView = MReportView.get(process.getAD_ReportView_ID());
				if(reportView != null && reportView.getAD_Table_ID() > 0)
					setAD_Table_ID(reportView.getAD_Table_ID());
			}
		}
		validate();
		return super.beforeSave(newRecord);
	}
	
	/**
	 * 	Get Process Drill Rule Parameters
	 *	@param reload true to reload from DB
	 *	@return process drill rule parameters
	 */
	public MProcessDrillRulePara[] getParameters (boolean reload)
	{
		if (!reload && m_parameter != null)
			return m_parameter;
		//
		final String whereClause = MProcessDrillRulePara.COLUMNNAME_AD_Process_DrillRule_ID+"=?";
		List<MProcessDrillRulePara> list = new Query(getCtx(), MProcessDrillRulePara.Table_Name, whereClause, get_TrxName())
		.setParameters(getAD_Process_DrillRule_ID())
		.setOnlyActiveRecords(true)
		.list();
		if (list.size() > 0 && is_Immutable())
			list.stream().forEach(e -> e.markImmutable());
		m_parameter = new MProcessDrillRulePara[list.size()];
		list.toArray(m_parameter);
		return m_parameter;
	}	//	getParameter

	@Override
	public PO markImmutable() {
		if (is_Immutable())
			return this;

		makeImmutable();
		return this;
	}
	
	/**
	 * @return true - all mandatory parameters are set; false - at least one mandatory parameter is not set
	 */
	private boolean allMandatoryParaSet() {
		boolean isValid = false;
		MProcess process = new MProcess(Env.getCtx(), getAD_Process_ID(), get_TrxName());
		for(MProcessPara processPara : process.getParameters()) {
			if(processPara.isMandatory() && processPara.getAD_Process_Para_ID() != getAD_Process_Para_ID()) {
				for(MProcessDrillRulePara drillRulePara : getParameters(true)) {
					if(drillRulePara.getAD_Process_Para_ID() == processPara.getAD_Process_Para_ID()) {
						String defPara = drillRulePara.getParameterDefault();
						String defParaTo = drillRulePara.getParameterToDefault();
						isValid = (processPara.isRange() && (!Util.isEmpty(defPara)) || (!Util.isEmpty(defParaTo))) ||
								(!processPara.isRange() && (!Util.isEmpty(defPara)));
						break;
					}
				}
				if(!isValid)
					return false;
				isValid = false;
			}
		}
		return true;
	}
	
	/**
	 * Is associated process (AD_Process_ID) has at least one mandatory process parameter
	 * @return boolean true if associated process (AD_Process_ID) has at least one mandatory process parameter
	 */
	public boolean hasMandatoryProcessPara() {
		MProcess process = new MProcess(Env.getCtx(), getAD_Process_ID(), null);
		for(MProcessPara processPara : process.getParameters()) {
			if(processPara.isMandatory())
				return true;
		}
		return false;
	}
	
	/**
	 * Validate Drill Rule - set IsValid
	 */
	public void validate() {
		if(getAD_Client_ID() == 0 && hasMandatoryProcessPara() && !SHOWHELP_ShowHelp.equalsIgnoreCase(getShowHelp())) {
			setIsValid(false);
		}
		else if(SHOWHELP_ShowHelp.equalsIgnoreCase(getShowHelp())) {
			setIsValid(true);
		}
		else {
			setIsValid(allMandatoryParaSet());
		}
	}
}