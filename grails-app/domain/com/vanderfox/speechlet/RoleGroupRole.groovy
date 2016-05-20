package com.vanderfox.speechlet

import grails.gorm.DetachedCriteria
import groovy.transform.ToString
import org.apache.commons.lang.builder.HashCodeBuilder

@ToString(cache=true, includeNames=true, includePackage=false)
class RoleGroupRole implements Serializable {

	private static final long serialVersionUID = 1

	RoleGroup roleGroup
	Role role

	RoleGroupRole(RoleGroup g, Role r) {
		this()
		roleGroup = g
		role = r
	}

	@Override
	boolean equals(other) {
		if (!(other instanceof RoleGroupRole)) {
			return false
		}

		other.role?.id == role?.id && other.roleGroup?.id == roleGroup?.id
	}

	@Override
	int hashCode() {
		def builder = new HashCodeBuilder()
		if (roleGroup) builder.append(roleGroup.id)
		if (role) builder.append(role.id)
		builder.toHashCode()
	}

	static RoleGroupRole get(long roleGroupId, long roleId) {
		criteriaFor(roleGroupId, roleId).get()
	}

	static boolean exists(long roleGroupId, long roleId) {
		criteriaFor(roleGroupId, roleId).count()
	}

	private static DetachedCriteria criteriaFor(long roleGroupId, long roleId) {
		RoleGroupRole.where {
			roleGroup == RoleGroup.load(roleGroupId) &&
			role == Role.load(roleId)
		}
	}

	static RoleGroupRole create(RoleGroup roleGroup, Role role, boolean flush = false) {
		def instance = new RoleGroupRole(roleGroup: roleGroup, role: role)
		instance.save(flush: flush, insert: true)
		instance
	}

	static boolean remove(RoleGroup rg, Role r, boolean flush = false) {
		if (rg == null || r == null) return false

		int rowCount = RoleGroupRole.where { roleGroup == rg && role == r }.deleteAll()

		if (flush) { RoleGroupRole.withSession { it.flush() } }

		rowCount
	}

	static void removeAll(Role r, boolean flush = false) {
		if (r == null) return

		RoleGroupRole.where { role == r }.deleteAll()

		if (flush) { RoleGroupRole.withSession { it.flush() } }
	}

	static void removeAll(RoleGroup rg, boolean flush = false) {
		if (rg == null) return

		RoleGroupRole.where { roleGroup == rg }.deleteAll()

		if (flush) { RoleGroupRole.withSession { it.flush() } }
	}

	static constraints = {
		role validator: { Role r, RoleGroupRole rg ->
			if (rg.roleGroup == null || rg.roleGroup.id == null) return
			boolean existing = false
			RoleGroupRole.withNewSession {
				existing = RoleGroupRole.exists(rg.roleGroup.id, r.id)
			}
			if (existing) {
				return 'roleGroup.exists'
			}
		}
	}

	static mapping = {
		id composite: ['roleGroup', 'role']
		version false
	}
}
