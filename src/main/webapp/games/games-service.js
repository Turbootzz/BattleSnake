export default class GamesService {
    async getGameIds() {
        const jwt = sessionStorage.getItem("JWT");
        const response = await fetch("/restservices/games", {
            headers: {
                "Authorization": "Bearer " + jwt
            }
        });
        if (!response.ok) throw new Error("Failed to fetch game IDs");
        return await response.json();
    }

    async getReplay(gameId) {
        const jwt = sessionStorage.getItem("JWT");
        const response = await fetch(`/restservices/games/${gameId}`, {
            headers: {
                "Authorization": "Bearer " + jwt,
                "Content-Type": "application/json"
            }
        });
        if (!response.ok) throw new Error('Failed to fetch game replay');
        return await response.json();
    }

    async removeReplay(gameId) {
        const jwt = sessionStorage.getItem("JWT");
        const response = await fetch(`/restservices/games/${gameId}`, {
            headers: {
                "Authorization": "Bearer " + jwt
            },
            method: 'DELETE'
        });
        if (!response.ok) throw new Error('Failed to delete game');
    }
}
