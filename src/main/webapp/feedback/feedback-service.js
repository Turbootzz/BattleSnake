export default class FeedbackService {
    async sendFeedback(feedback) {
        const jwt = sessionStorage.getItem("JWT");
        const response = await fetch("/restservices/feedback", {
            method: "POST",
            headers: {
                "Authorization": "Bearer " + jwt,
                "Content-Type": "application/json"
            },
            body: JSON.stringify(feedback)
        });
        if (!response.ok) throw new Error("Failed to submit feedback");
        return await response.text();
    }

    async getAllFeedback() {
        const jwt = sessionStorage.getItem("JWT");
        const response = await fetch("/restservices/feedback", {
            headers: {
                "Authorization": "Bearer " + jwt
            }
        });
        if (!response.ok) throw new Error("Failed to fetch feedback");
        return await response.json();
    }

    async getFeedback(id) {
        const jwt = sessionStorage.getItem("JWT");
        const response = await fetch(`/restservices/feedback/${id}`, {
            headers: {
                "Authorization": "Bearer " + jwt
            }
        });
        if (!response.ok) throw new Error('Failed to fetch feedback');
        return await response.json();
    }

    async removeFeedback(id) {
        const jwt = sessionStorage.getItem("JWT");
        const response = await fetch(`/restservices/feedback/${id}`, {
            method: 'DELETE',
            headers: {
                "Authorization": "Bearer " + jwt
            }
        });
        if (!response.ok) throw new Error('Failed to delete feedback');
    }
}