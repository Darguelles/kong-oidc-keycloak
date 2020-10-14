# Kong + OpenID + Keycloak

This is a functional example of how to use Kong opensource distribution with 
[kong-oidc](https://github.com/nokia/kong-oidc) plugin.

## Running containers
The first time you'll need to run the migrations on the kong-db container before any other. The
[Makefile](Makefile) has a set of commands to start the setup of your environment.

## Running the examples
#### Web
##### Kong config
In the [Makefile](Makefile), execute `prepare_mock_service`. It will create a new service and route in Kong to 
redirect your requests to Mockbin. More detail in this guide: 
[exposing services in Kong](https://docs.konghq.com/getting-started-guide/2.1.x/expose-services/)

##### Keycloak config
Once the containers are running, you need to start configuring your Keycloak server. The first step is to login
with the default credentials (admin/admin) to the administration console. Then you need to:
- Create Client:
    - Menu `Clients` > click `Create` button
    - Client ID: kong
    - Click `Save` button.
    - In **Settings** tab:
        - Access type: `Confidential`
        - Root URL: `http://localhost:8000`
        - Valid Redirect URL: `/mock/*`
        - Click `Save` button.
    - In **Credentials** tab:
        - Copy the Secret value (You will need it to configure the Kong plugin)

- Create User
    - Menu `Users` > click `Add user` button
    - Username: user, enable the Email verified switch.
    - Click `Save` button.
    - In **Credentials** tab:
        - Set password, disable the Temporary credentials switch.
        - Click `Reset Password` button.

##### Configure the OIDC plugin on Kong
In the [Makefile](Makefile), execute `configure_client_kong_keycloak`. You will need to replace the 
`<client_secret_from_keycloak>` value with the generated client secret.

You can verify the configuration executing `check_kong_oidc_config`.

##### Test
Enter to `http://localhost:8000/mock`, you should see the Keycloak login page. Enter the user credentials and you 
should be redirected to the Mockbin page. 





