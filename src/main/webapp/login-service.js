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
        sessionStorage.removeItem("JWT");
        return Promise.resolve();
    }
}
