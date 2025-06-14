export default class SnakeService {
    async getSnake() {
        const jwt = sessionStorage.getItem("JWT");
        const response = await fetch("/restservices/appearance", {
            headers: {
                "Authorization": "Bearer " + jwt
            }
        });
        if (!response.ok) throw new Error("Failed to fetch snake appearance");
        return await response.json();
    }

    async updateSnake(updatedSnake) {
        const response = await fetch ("/restservices/appearance", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(updatedSnake)
        });
        return response.ok;
    }
}
