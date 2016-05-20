package com.vanderfox.speechlet

class SecurityInfoController extends grails.plugin.springsecurity.ui.SecurityInfoController {

    def index() {
        return super.config()
    }
    @Override
    def config() {
        return super.config()
    }

    @Override
    def mappings() {
        return super.mappings()
    }

    @Override
    def currentAuth() {
        return super.currentAuth()
    }

    @Override
    def usercache() {
        return super.usercache()
    }

    @Override
    def filterChains() {
        return super.filterChains()
    }

    @Override
    def logoutHandlers() {
        return super.logoutHandlers()
    }

    @Override
    def voters() {
        return super.voters()
    }

    @Override
    def providers() {
        return super.providers()
    }

    @Override
    def secureChannel() {
        return super.secureChannel()
    }
}
