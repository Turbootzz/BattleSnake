export default class LoginService {
    isLoggedIn() {
        return sessionStorage.getItem("JWT") !== null;
    }

    login(user, password) {
        return fetch("/restservices/authentication", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ username: user, password: password })
        })
            .then(response => {
                if (!response.ok) throw new Error("Login failed!");
                return response.json();
            })
            .then(data => {
                if (data.JWT) {
                    sessionStorage.setItem("JWT", data.JWT);
                } else {
                    throw new Error("JWT token not returned by server");
                }
            });
    }

    getUser() {
        //TODO: deze GET method test je token op server-side problemen. Je kunt client-side op zich wel 'ingelogd' zijn
        //maar het zou altijd zomaar kunnen dat je token verlopen is, of dat er server-side iets anders aan de hand is.
        //Dus het is handig om te checken met een -echte fetch- of je login-token wel echt bruikbaar is.
        const jwt = sessionStorage.getItem("JWT");
        if (!jwt) return Promise.resolve(null);

        return fetch("/restservices/user", {
            headers: {
                "Authorization": "Bearer " + jwt
            }
        }).then(response => {
            if (!response.ok) return null;
            return response.json();
        }).catch(() => null);
    }

    logout() {
        //TODO: hoe ga je eigenlijk iemand 'uitloggen'?
        sessionStorage.removeItem("JWT");
        return Promise.resolve();
    }
}
