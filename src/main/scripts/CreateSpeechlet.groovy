/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
description("Creates a new Alexa skill Speechlet") {
    usage "grails create-speechlet [speechletName]"
    argument name:'Speechlet Name', description:"The name of the speechlet"
}

model = model( args[0] )
render  template:"Speechlet.groovy",
        destination: file( "grails-app/speechlet/$model.packagePath/${model.simpleName}Speechlet.groovy"),
        model: model
