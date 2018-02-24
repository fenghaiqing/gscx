package cn.migu.income.pojo;

public class MiguRoles
{
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_MIGU_ROLES.ROLE_ID
     *
     * @mbggenerated Tue Jan 05 10:03:59 CST 2016
     */
    private Long roleId;
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_MIGU_ROLES.ROLE_NAME
     *
     * @mbggenerated Tue Jan 05 10:03:59 CST 2016
     */
    private String roleName;
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_MIGU_ROLES.CRTATE_DATE
     *
     * @mbggenerated Tue Jan 05 10:03:59 CST 2016
     */
    private String crtateDate;
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_MIGU_ROLES.IS_ADMIN
     *
     * @mbggenerated Tue Jan 05 10:03:59 CST 2016
     */
    private Short isAdmin;
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_MIGU_ROLES.ROLE_ID
     *
     * @return the value of T_MIGU_ROLES.ROLE_ID
     *
     * @mbggenerated Tue Jan 05 10:03:59 CST 2016
     */
    public Long getRoleId()
    {
        return roleId;
    }
    
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_MIGU_ROLES.ROLE_ID
     *
     * @param roleId the value for T_MIGU_ROLES.ROLE_ID
     *
     * @mbggenerated Tue Jan 05 10:03:59 CST 2016
     */
    public void setRoleId(Long roleId)
    {
        this.roleId = roleId;
    }
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_MIGU_ROLES.ROLE_NAME
     *
     * @return the value of T_MIGU_ROLES.ROLE_NAME
     *
     * @mbggenerated Tue Jan 05 10:03:59 CST 2016
     */
    public String getRoleName()
    {
        return roleName;
    }
    
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_MIGU_ROLES.ROLE_NAME
     *
     * @param roleName the value for T_MIGU_ROLES.ROLE_NAME
     *
     * @mbggenerated Tue Jan 05 10:03:59 CST 2016
     */
    public void setRoleName(String roleName)
    {
        this.roleName = roleName == null ? null : roleName.trim();
    }
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_MIGU_ROLES.CRTATE_DATE
     *
     * @return the value of T_MIGU_ROLES.CRTATE_DATE
     *
     * @mbggenerated Tue Jan 05 10:03:59 CST 2016
     */
    public String getCrtateDate()
    {
        return crtateDate;
    }
    
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_MIGU_ROLES.CRTATE_DATE
     *
     * @param crtateDate the value for T_MIGU_ROLES.CRTATE_DATE
     *
     * @mbggenerated Tue Jan 05 10:03:59 CST 2016
     */
    public void setCrtateDate(String crtateDate)
    {
        this.crtateDate = crtateDate;
    }
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_MIGU_ROLES.IS_ADMIN
     *
     * @return the value of T_MIGU_ROLES.IS_ADMIN
     *
     * @mbggenerated Tue Jan 05 10:03:59 CST 2016
     */
    public Short getIsAdmin()
    {
        return isAdmin;
    }
    
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_MIGU_ROLES.IS_ADMIN
     *
     * @param isAdmin the value for T_MIGU_ROLES.IS_ADMIN
     *
     * @mbggenerated Tue Jan 05 10:03:59 CST 2016
     */
    public void setIsAdmin(Short isAdmin)
    {
        this.isAdmin = isAdmin;
    }
    
    @Override
    public String toString()
    {
        return " roleId:" + roleId + ", roleName:" + roleName + ", crtateDate:" + crtateDate + ", isAdmin:" + isAdmin;
    }
}