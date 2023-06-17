# compose-my-pokedex
A Gen 1 Pokemon Pokedex Built with Jetpack Compose - WIP

SUCCESSES:
    #_100% working 
    #_added basic search capability
    #_applied basic application icon
    #_loads images asynchronously via coil
    #_duplicate items in database are overwritten
    #_creates internal list of pokemon [1-151] no problem
    #_serializes pokemon data classes via kotlinx.serializer
    #_viewmodel keeps data current through configuration changes
    #_viewmodel is passed as argument to app ui for easy data retrieval
    #_added lower nav bar for additional choices [details / search / quit]
    #_sends get requests to [2] different apis for data and icons via retrofit
    #_class converter created for handlinng list & map storage in room data base
    #_DOWLOADED DATA IS SAVED INTERNALLY UPON APPLICATION DESTRUCTION VIA ROOM DATABASE
    #_DATABASE CLASS CREATED USING SINGLETON METHOD & INITIALIZED UPON VIEWMODEL STARTUP
    #_CALLS TO THE DATABASE ARE MADE ASYNCHRONOUSLY IN ORDER TO OPERATE ON THE MAIN THREAD


ISSUES:
    #_SCREEN ROTATION IS DEACTIVATED
    #_RECONSTRUCT UI-STATE INTERFACE
    #_POKEMON DESCRIPTION LANGUAGE CHANGE AFTER ID-151
    #_APP MUST RESTART IF INTERNET ACCESS IS LOST DURING BUILD
    #_ONLY 2/4 OF EACH ITEM'S INFORMATION IS BEING DISPLAYED / USED


TO-DOS:
    #_ADD ADDITIONAL POKEMON TO THE LIST [1-500]
    #_ADD "ALLOW INTERNET PERMISSIONS" UPON APP INITIAL STARTUP
    #_CONSTRUCT THE UI DETAILS PAGE TO DISPLAY INFORMATION NOT BEING USED
    #_IMPLEMENT ADDITIONAL JSON SERIALIZERS FOR DESCRIPTION LANGUAGE CHANGES
    #_APPLY SCREEN ORIENTATIONS FOR HANDLING VARIOUS SCREEN SIZES AND ROTATIONS
