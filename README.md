# compose-my-pokedex
A Gen 1 Pokemon Pokedex Built with Android Jetpack Compose – WIP

SUCCESSES: 
    –––#_100% working
    –––#_added basic search capability
    –––#_applied basic application icon
    –––#_loads images asynchronously via coil
    –––#_duplicate items in database are overwritten
    –––#_creates internal list of pokemon [1-151] no problem
    –––#_serializes pokemon data classes via kotlinx.serializer
    –––#_viewmodel keeps data current through configuration changes
    –––#_viewmodel is passed as argument to app ui for easy data retrieval
    –––#_added lower nav bar for additional choices [details / search / quit]
    –––#_sends get requests to [2] different apis for data and icons via retrofit
    –––#_class converter created for handlinng list & map storage in room database
    –––#_calls to database are made asynchronously in order to operate on the main thread
    –––#_downloaded data is saved internally upon application destruction via room database
    –––#_database class created using singleton method & initialized upon viewmodel startup


ISSUES:
    –––#_screen rotation is deactivated
    –––#_internet permissions prompt not implemented
    –––#_reconstruction of ui-state interface is needed
    –––#_pokemon description language change after id-151
    –––#_only 2/4 of each item's information is being displayed / used
    –––#_app must restart if internet access is lost during first build


TO-DOS:
    –––#_add additional pokemon to the list [1-500]
    –––#_add "allow internet permissions" upon initial app startup
    –––#_construct the ui details page to display information not being shown
    –––#_implement additional json serializers for description language changes
    –––#_apply screen orientation handling for various screen sizes & rotations
