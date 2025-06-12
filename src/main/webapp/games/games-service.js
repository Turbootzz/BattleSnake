export default class GamesService {
    async getGameIds() {
        //TODO: fetch alle games van de de service, idealiter zonder alle details
        const response = await fetch('/restservices/games');
        if (!response.ok) throw new Error('Failed to fetch game IDs');
        return await response.json();
    }

    async getReplay(gameId) {
        //TODO: fetch de details van een enkele game. Let wel, het staat vrij wat voor informatie je precies toont
        //zolang je maar laat zien dat je data kunt opslaan over meerdere zetten heen. Dus deze dummy-data is puur
        //ter illustratie.
        const response = await fetch(`/restservices/games/${gameId}`);
        if (!response.ok) throw new Error('Failed to fetch game replay');
        return await response.json();
    }

    async removeReplay(gameId) {
        //TODO: gebruik fetch om een enkele game (bij de server) te deleten
        const response = await fetch(`/restservices/games/${gameId}`, { method: 'DELETE' });
        if (!response.ok) throw new Error('Failed to delete game');
    }
}
