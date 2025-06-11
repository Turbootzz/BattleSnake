export default class SnakeService {
    async getSnake() {
        const response = await fetch("http://localhost:8080/restservices/appearance");
        return await response.json();
    }

    async updateSnake(updatedSnake) {
        const response = await fetch ("http://localhost:8080/restservices/appearance", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(updatedSnake)
        });
        return response.ok;
    }
}
