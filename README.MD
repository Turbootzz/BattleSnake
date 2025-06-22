# BEP1 Eindopdracht 2025

## Battlesnake

### Inleiding

[Battlesnake](https://play.battlesnake.com) is een multiplayer code-challenge waarbij je je eigen Snake '*AI*' moet
programmeren als webservice.

Het is een spelletje dat de meesten van jullie vast wel een keer gezien zullen hebben: je bestuurt een slang die door
blokjes te eten steeds langer wordt. De slang mag nooit zichzelf, de rand of een andere slang raken.

### Onderdelen

De opdracht heeft een aantal verplichte onderdelen:

1. De Battlesnake moet op je eigen account geregistreerd kunnen worden op https://play.battlesnake.com. Hiervoor zul je
dus de applicatie echt toegankelijk moeten maken (via bijv. Heroku, Azure, of je eigen slimme idee...) en de
[Battlesnake API](https://docs.battlesnake.com/references/api#the-battlesnake-api) moeten implementeren. Focus je eerst 
op het GET request. Je zou bijv. in eerste instantie een hard-coded response kunnen teruggeven (wat je in het volgende
onderdeel direct moet verbeteren). De documentatie is er niet helemaal duidelijk over, maar je GET request hoeft niet op
een 'top-level-url' gehost te worden, je mag je slang prima parkeren op `/restservices/bepslang` (of iets dergelijks).
2. Het uiterlijk van je slang moet via de snake.html (of vergelijkbare) frontend aangepast kunnen worden. Het is oké om
daarna handmatig op het refresh-knopje op Battlesnake.com te klikken, dat hoeft, en kan waarschijnlijk ook niet automatisch!.
Oftewel, na een aanpassing moet het GET request van onderdeel 1 een nieuw uiterlijk opleveren.
3. Uiteraard moet je ook een potje kunnen spelen! Hiervoor zul je de rest van de 
[Battlesnake API](https://docs.battlesnake.com/references/api#the-battlesnake-api) moeten implementeren. Dit houdt in dat
je naast dat ene GET request, ook 3 POST-requests moet kunnen ontvangen. Het is hierbij niet van groot belang hoe slim je slang is, maar
of je deze requests netjes kunt opvangen en afhandelen. Er staat in **DummyBody.json** een voorbeeld-body voor je klaar zodat
je die zo in Postman kan plakken en niet de requests van Battlesnake hoeft te reverse-engineeren.
4. Informatie van gespeelde potjes moet (per potje) inzichtelijk zijn via de games.html (of vergelijkbare) frontend.
Welke informatie is niet heel belangrijk (denk bijv. aan 'aantal beurten', 'welke moves zijn er gedaan', etc.), het gaat 
er om dat je makkelijk kan bewijzen dat je alle moves van #3 goed kan opvangen en er data over kan verzamelen.
5. De informatie van een gespeelde potje moet verwijderd kunnen worden (omdat deze bijv. niet interessant was). Hiervoor
staat ook al een beginnetje klaar op de pagina: games.html (uiteraard staat het je vrij dit te verbeteren)
6. Waar logisch moet een gebruiker eerst inloggen voordat er slangen gewijzigd en gegevens ingezien en/of verwijderd kunnen worden.

Dit startproject bevat een aantal bestanden om je alvast op weg te helpen. Schrik niet van de verhouding Java en Javascript,
we geven veel Javascript code mee, omdat juist Javascript primair bij FEP hoort, terwijl wij puur de koppeling tussen de 
twee willen zien. Zo goed als alle Java zul je zelf moeten schrijven.
De urls die je gebruikt zijn vrij, en zowel aan de Battlesnake als de frontend kant los te configureren.

### Credits
Gemaakt door: Thijs Herman