package com.vanderfox.speechlet

import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.annotation.Secured

class UserController extends grails.plugin.springsecurity.ui.UserController {


    def springSecurityService
    def springSecurityUiService


    @Override
    def create() {
        return super.create()
    }

    @Override
    def save() {
        return super.save()
    }

    @Override
    @Secured(['ROLE_USER'])
    def edit() {
        if (!params.id) {
            User currentUser = springSecurityService.currentUser
            params.put("id",currentUser.id.toString())
        } else {
            User currentUser = springSecurityService.currentUser
            if (currentUser.id != params.id && !SpringSecurityUtils.ifAllGranted("ROLE_ADMIN")) {

                return super.edit()
            }

        }
        return super.edit()
    }

    @Override
    @Secured (["ROLE_USER"])
    def update() {

        User currentUser = springSecurityService.currentUser
        if (!params.id) {

            params.put("id",currentUser.id.toString())
        } else {

            if (currentUser.id != params?.id?.toLong() && !SpringSecurityUtils.ifAllGranted("ROLE_ADMIN")) {
                render(view: '/login/denied')
                return
            }

        }
        if (!SpringSecurityUtils.ifAllGranted("ROLE_ADMIN")) {
            // they can only update password
            HashMap restrictedProperties = new HashMap()
            if (params.password) {
                restrictedProperties.put("password",params.password)
            }
            springSecurityUiService.updateUser(params,currentUser)
            flash.message = "Password successfully changed"
            if (!params.id) {
                params.put("id",currentUser.id.toString())
            }
            render(view: 'edit', model:[user:currentUser])
            //return super.edit()
        } else {
            // can update roles and such
            return super.update()
        }
    }

    @Override
    @Secured (["ROLE_ADMIN"])
    def delete() {
        return super.delete()
    }

    @Override
    @Secured (["ROLE_ADMIN"])
    def search() {
        return super.search()
    }

}
