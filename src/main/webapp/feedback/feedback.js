import FeedbackService from "./feedback-service.js";

const feedbackList = document.getElementById('feedback');
const feedbackService = new FeedbackService();
const details = document.getElementById('details');

window.addEventListener('hashchange', () => {
    loadFeedback(window.location.hash);
});

function loadFeedback(hashId) {
    const id = hashId.substring(1);
    return feedbackService.getFeedback(id).then(feedbackDetails => {
        if (feedbackDetails) {
            const text = details.querySelector('pre');
            text.textContent = JSON.stringify(feedbackDetails, null, 2);
            details.classList.remove('hidden');
        } else {
            details.classList.add('hidden');
        }
    });
}

async function refresh() {
    feedbackList.innerHTML = '';
    const ids = await feedbackService.getAllFeedback();
    ids.forEach((feedback, index) => {
        const li = document.createElement('li');
        li.innerHTML = `<a href='#${index}'>Feedback from ${feedback.username || 'anonymous'}</a><span class="delete">&#x2716;</span>`;
        li.querySelector('.delete').addEventListener("click", () => {
            feedbackService.removeFeedback(index).then(refresh);
        });
        feedbackList.appendChild(li);
    });
}

document.getElementById("feedback-form").addEventListener("submit", e => {
    e.preventDefault();
    const message = e.target.message.value;
    feedbackService.sendFeedback({ message }).then(() => {
        e.target.reset();
        refresh();
    });
});

refresh().then(() => {
    if (window.location.hash) {
        loadFeedback(window.location.hash);
    }
});