/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Msg;
import org.compiere.util.Util;

/**
 *	Role Organization Access Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MRoleOrgAccess.java,v 1.3 2006/07/30 00:58:38 jjanke Exp $
 */
public class MRoleOrgAccess extends X_AD_Role_OrgAccess
{
	/**
	 * generated serial id
	 */
	private static final long serialVersionUID = 4664267788838719168L;

	/**
	 * 	Get Organizational Access of Role
	 *	@param ctx context
	 *	@param AD_Role_ID role
	 *	@return array of Role Organization Access
	 */
	public static MRoleOrgAccess[] getOfRole (Properties ctx, int AD_Role_ID)
	{
		return get (ctx, "SELECT * FROM AD_Role_OrgAccess WHERE AD_Role_ID=?", AD_Role_ID);	
	}	//	getOfRole

	/**
	 * 	Get Organizational Access of Client
	 *	@param ctx context
	 *	@param AD_Client_ID client
	 *	@return array of Role Organization Access
	 */
	public static MRoleOrgAccess[] getOfClient (Properties ctx, int AD_Client_ID)
	{
		return get (ctx, "SELECT * FROM AD_Role_OrgAccess WHERE AD_Client_ID=?", AD_Client_ID);	
	}	//	getOfClient

	/**
	 * 	Get Organizational Access of Organization
	 *	@param ctx context
	 *	@param AD_Org_ID organization
	 *	@return array of Role Organization Access
	 */
	public static MRoleOrgAccess[] getOfOrg (Properties ctx, int AD_Org_ID)
	{
		return get (ctx, "SELECT * FROM AD_Role_OrgAccess WHERE AD_Org_ID=?", AD_Org_ID);	
	}	//	getOfOrg
	
	/**
	 * 	Get Organizational Access for Role
	 *	@param ctx context
	 *	@param sql SQL select clause
	 *	@param id id parameter for SQL clause
	 *	@return array of Role Organization Access
	 */
	private static MRoleOrgAccess[] get (Properties ctx, String sql, int id)
	{
		ArrayList<MRoleOrgAccess> list = new ArrayList<MRoleOrgAccess>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt (1, id);
			rs = pstmt.executeQuery ();
			while (rs.next ())
				list.add (new MRoleOrgAccess(ctx, rs, null));
		}
		catch (Exception e)
		{
			s_log.log(Level.SEVERE, "get", e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		MRoleOrgAccess[] retValue = new MRoleOrgAccess[list.size ()];
		list.toArray (retValue);
		return retValue;
	}	//	get
	
	/**
	 * 	Create Organizational Access for all Automatic Roles (IsManual=N)
	 *	@param org organization
	 *	@return true if created
	 */
	public static boolean createForOrg (MOrg org)
	{
		int counter = 0;
		MRole[] roles = MRole.getOfClient(org.getCtx(), org.get_TrxName());
		for (int i = 0; i < roles.length; i++)
		{
			if (!roles[i].isManual())
			{
				MRoleOrgAccess orgAccess = new MRoleOrgAccess (org, roles[i].getAD_Role_ID());
				if (orgAccess.save())
					counter++;
			}
		}
		if (s_log.isLoggable(Level.INFO)) s_log.info(org + " - created #" + counter);
		return counter != 0;
	}	//	createForOrg
	
	/**	Static Logger	*/
	private static CLogger	s_log	= CLogger.getCLogger (MRoleOrgAccess.class);
	
	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MRoleOrgAccess (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MRoleOrgAccess

    /**
     * UUID based Constructor
     * @param ctx  Context
     * @param AD_Role_OrgAccess_UU  UUID key
     * @param trxName Transaction
     */
    public MRoleOrgAccess(Properties ctx, String AD_Role_OrgAccess_UU, String trxName) {
        super(ctx, AD_Role_OrgAccess_UU, trxName);
		if (Util.isEmpty(AD_Role_OrgAccess_UU))
			setInitialDefaults();
    }

	/**
	 *	@param ctx context
	 *	@param ignored ignored
	 *	@param trxName transaction
	 */
	public MRoleOrgAccess (Properties ctx, int ignored, String trxName)
	{
		super(ctx, 0, trxName);
		if (ignored != 0)
			throw new IllegalArgumentException("Multi-Key");
		setInitialDefaults();
	}	//	MRoleOrgAccess
	
	/**
	 * Set the initial defaults for a new record
	 */
	private void setInitialDefaults() {
		setIsReadOnly(false);
	}

	/**
	 * 	Organization Constructor
	 *	@param org organization
	 *	@param AD_Role_ID role
	 */
	public MRoleOrgAccess (MOrg org, int AD_Role_ID)
	{
		this (org.getCtx(), 0, org.get_TrxName());
		setClientOrg (org);
		setAD_Role_ID (AD_Role_ID);
	}	//	MRoleOrgAccess

	/**
	 * 	Role Constructor
	 *	@param role role
	 *	@param AD_Org_ID organization
	 */
	public MRoleOrgAccess (MRole role, int AD_Org_ID)
	{
		this (role.getCtx(), 0, role.get_TrxName());
		setClientOrg (role.getAD_Client_ID(), AD_Org_ID);
		setAD_Role_ID (role.getAD_Role_ID());
	}	//	MRoleOrgAccess

	/**
	 * 	String Representation
	 *	@return info
	 */
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder("MRoleOrgAccess[");
		sb.append("AD_Role_ID=").append(getAD_Role_ID())
			.append(",AD_Client_ID=").append(getAD_Client_ID())
			.append(",AD_Org_ID=").append(getAD_Org_ID())
			.append(",RO=").append(isReadOnly());	
		sb.append("]");
		return sb.toString();
	}	//	toString
	
	/**
	 * 	Extended String Representation
	 * 	@param ctx context
	 *	@return extended info
	 */
	public String toStringX (Properties ctx)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(Msg.translate(ctx, "AD_Client_ID")).append("=").append(getClientName()).append(" - ")
			.append(Msg.translate(ctx, "AD_Org_ID")).append("=").append(getOrgName());	
		return sb.toString();
	}	//	toStringX

	private String	m_clientName;
	private String	m_orgName;
	
	/**
	 * 	Get Client Name
	 *	@return client name
	 */
	public String getClientName()
	{
		if (m_clientName == null)
		{
			String sql = "SELECT c.Name, o.Name "
				+ "FROM AD_Client c INNER JOIN AD_Org o ON (c.AD_Client_ID=o.AD_Client_ID) "
				+ "WHERE o.AD_Org_ID=?";
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement(sql, null);
				pstmt.setInt(1, getAD_Org_ID());
				rs = pstmt.executeQuery();
				if (rs.next())
				{
					m_clientName = rs.getString(1);
					m_orgName = rs.getString(2);
				}
			}
			catch (Exception e)
			{
				log.log(Level.SEVERE, "getClientName", e);
			}
			finally
			{
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}
		}
		return m_clientName;
	}	//	getClientName
	
	/**
	 * 	Get organization Name
	 *	@return organization name
	 */
	public String getOrgName()
	{
		if (m_orgName == null)
			getClientName();
		return m_orgName;
	}	//	getOrgName

}	//	MRoleOrgAccess
