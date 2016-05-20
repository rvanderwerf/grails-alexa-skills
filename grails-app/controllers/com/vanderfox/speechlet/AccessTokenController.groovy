package com.vanderfox.speechlet

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import org.springframework.http.HttpStatus

@Transactional(readOnly = true)
class AccessTokenController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured (["ROLE_ADMIN"])
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond AccessToken.list(params), model:[accessTokenCount: AccessToken.count()]
    }

    @Secured (["ROLE_ADMIN"])
    def show(AccessToken accessToken) {
        respond accessToken
    }

    @Secured (["ROLE_ADMIN"])
    def create() {
        respond new AccessToken(params)
    }


    @Transactional
    @Secured (["ROLE_ADMIN"])
    def save(AccessToken accessToken) {
        if (accessToken == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (accessToken.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond accessToken.errors, view:'create'
            return
        }

        accessToken.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'accessToken.label', default: 'com.vanderfox.speechlet.AccessToken'), accessToken.id])
                redirect accessToken
            }
            '*' { respond accessToken, [status: HttpStatus.CREATED] }
        }
    }

    @Secured (["ROLE_ADMIN"])
    def edit(AccessToken accessToken) {
        respond accessToken
    }

    @Transactional
    @Secured (["ROLE_ADMIN"])
    def update(AccessToken accessToken) {
        if (accessToken == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (accessToken.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond accessToken.errors, view:'edit'
            return
        }

        accessToken.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'accessToken.label', default: 'com.vanderfox.speechlet.AccessToken'), accessToken.id])
                redirect accessToken
            }
            '*'{ respond accessToken, [status: HttpStatus.OK] }
        }
    }

    @Transactional
    @Secured (["ROLE_ADMIN"])
    def delete(AccessToken accessToken) {

        if (accessToken == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        accessToken.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'accessToken.label', default: 'com.vanderfox.speechlet.AccessToken'), accessToken.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: HttpStatus.NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'accessToken.label', default: 'com.vanderfox.speechlet.AccessToken'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: HttpStatus.NOT_FOUND }
        }
    }
}
