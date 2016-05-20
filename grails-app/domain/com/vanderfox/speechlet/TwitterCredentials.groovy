package com.vanderfox.speechlet

class TwitterCredentials {

    static constraints = {
        user(nullable: false)
        consumerKey(nullable: false)
        consumerSecret(nullable: false)
        accessToken(nullable:false)
        accessTokenSecret(nullable: false)
        active(nullable:false)
    }
    User user
    String consumerKey
    String consumerSecret
    String accessToken
    String accessTokenSecret
    Boolean active = true

}
