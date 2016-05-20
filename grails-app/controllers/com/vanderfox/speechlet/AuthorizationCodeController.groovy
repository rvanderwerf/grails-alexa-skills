package com.vanderfox.speechlet

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import org.springframework.http.HttpStatus

@Transactional(readOnly = true)
class AuthorizationCodeController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured (["ROLE_ADMIN"])
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond AuthorizationCode.list(params), model:[authorizationCodeCount: AuthorizationCode.count()]
    }

    @Secured (["ROLE_ADMIN"])
    def show(AuthorizationCode authorizationCode) {
        respond authorizationCode
    }

    @Secured (["ROLE_ADMIN"])
    def create() {
        respond new AuthorizationCode(params)
    }

    @Transactional
    @Secured (["ROLE_ADMIN"])
    def save(AuthorizationCode authorizationCode) {
        if (authorizationCode == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (authorizationCode.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond authorizationCode.errors, view:'create'
            return
        }

        authorizationCode.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'authorizationCode.label', default: 'com.vanderfox.speechlet.AuthorizationCode'), authorizationCode.id])
                redirect authorizationCode
            }
            '*' { respond authorizationCode, [status: HttpStatus.CREATED] }
        }
    }

    @Secured (["ROLE_ADMIN"])
    def edit(AuthorizationCode authorizationCode) {
        respond authorizationCode
    }

    @Transactional
    @Secured (["ROLE_ADMIN"])
    def update(AuthorizationCode authorizationCode) {
        if (authorizationCode == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (authorizationCode.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond authorizationCode.errors, view:'edit'
            return
        }

        authorizationCode.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'authorizationCode.label', default: 'com.vanderfox.speechlet.AuthorizationCode'), authorizationCode.id])
                redirect authorizationCode
            }
            '*'{ respond authorizationCode, [status: HttpStatus.OK] }
        }
    }

    @Transactional
    @Secured (["ROLE_ADMIN"])
    def delete(AuthorizationCode authorizationCode) {

        if (authorizationCode == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        authorizationCode.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'authorizationCode.label', default: 'com.vanderfox.speechlet.AuthorizationCode'), authorizationCode.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: HttpStatus.NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'authorizationCode.label', default: 'com.vanderfox.speechlet.AuthorizationCode'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: HttpStatus.NOT_FOUND }
        }
    }
}
