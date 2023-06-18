# COMPOSE-MY-POKEDEX:
## Jetpack Compose Project – WIP

A Generation I Pokémon Pokédex Built with [Android Jetpack Compose](https://developer.android.com/jetpack/compose) & [Kotlin](https://kotlinlang.org/) &nbsp;
## Features
- Search functionality
- Generation IV Johto pokédex design
- Dual upper/lower UI on a single screen
- All 151 Gen 1 pokémon general stat information
- Collects pokémon data & large sprites from [PokéAPI](https://pokeapi.co/)
- Collects pokémon mini-sprites from [Pokémon Database](https://pokemondb.net/sprites) &nbsp;
&nbsp;

##### Progress:
- 100% working
- added basic search capability 
- applied basic application icon 
- loads images asynchronously via coil 
- duplicate items in database are overwritten 
- creates internal list of pokemon [1-151] no problem 
- serializes pokemon data classes via kotlinx.serializer 
- viewmodel keeps data current through configuration changes 
- viewmodel is passed as argument to app ui for easy data retrieval 
- added lower nav bar for additional choices [details / search / quit] 
- sends get requests to [2] different apis for data and icons via retrofit 
- class converter created for handlinng list & map storage in room database 
- calls to database are made asynchronously in order to operate on the main thread
- database class created using singleton method & initialized upon viewmodel startup
- downloaded data is saved internally upon application destruction via room database &nbsp;

##### Issues:
- screen rotation is deactivated 
- reconstruction of ui-state interface is needed
- internet permissions prompt not implemented 
- pokemon description language change after id-151 
- app must restart if internet access is lost during first build
- only 2/4 of each item's information is being displayed / used &nbsp;

##### To-Dos:
- add additional pokemon to the list [1-500] 
- add "allow internet permissions" upon initial app startup 
- construct the ui details page to display information not being shown 
- apply screen orientation handling for various screen sizes & rotations
- implement additional json serializers for description language changes &nbsp;
&nbsp;

## Dependencies
- [Coil](https://coil-kt.github.io/coil/compose/) - An image loading library for Android backed by Kotlin Coroutines.
- [Retrofit](https://github.com/square/retrofit) - A type-safe HTTP client for Android and Java.
- [Kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) - Compiler plugin that generates visitor code for serializable classes.
- [Kotlin Serialization Converter](https://github.com/JakeWharton/retrofit2-kotlinx-serialization-converter) - A Retrofit 2 Converter.Factory for Kotlin serialization.
- [Java Scalars Converter](https://github.com/square/retrofit/tree/master/retrofit-converters/scalars) - A Converter which supports converting strings and both primitives and their boxed types to text/plain bodies.
- [Room Persistence Library](https://developer.android.com/training/data-storage/room) - Provides the convenience of data caching for offline browsing.
- [System UI Controller](https://google.github.io/accompanist/systemuicontroller/) - Adds utilities for updating the System UI bar colors within Jetpack Compose.



[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does its job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)

   [dill]: <https://github.com/joemccann/dillinger>
   [git-repo-url]: <https://github.com/joemccann/dillinger.git>
   [john gruber]: <http://daringfireball.net>
   [df1]: <http://daringfireball.net/projects/markdown/>
   [markdown-it]: <https://github.com/markdown-it/markdown-it>
   [Ace Editor]: <http://ace.ajax.org>
   [node.js]: <http://nodejs.org>
   [Twitter Bootstrap]: <http://twitter.github.com/bootstrap/>
   [jQuery]: <http://jquery.com>
   [@tjholowaychuk]: <http://twitter.com/tjholowaychuk>
   [express]: <http://expressjs.com>
   [AngularJS]: <http://angularjs.org>
   [Gulp]: <http://gulpjs.com>

   [PlDb]: <https://github.com/joemccann/dillinger/tree/master/plugins/dropbox/README.md>
   [PlGh]: <https://github.com/joemccann/dillinger/tree/master/plugins/github/README.md>
   [PlGd]: <https://github.com/joemccann/dillinger/tree/master/plugins/googledrive/README.md>
   [PlOd]: <https://github.com/joemccann/dillinger/tree/master/plugins/onedrive/README.md>
   [PlMe]: <https://github.com/joemccann/dillinger/tree/master/plugins/medium/README.md>
   [PlGa]: <https://github.com/RahulHP/dillinger/blob/master/plugins/googleanalytics/README.md>
