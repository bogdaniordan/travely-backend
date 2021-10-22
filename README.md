<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Thanks again! Now go create something AMAZING! :D
-->



<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->
<!-- [![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![LinkedIn - Marius][linkedin-shield]][linkedin-marius-url]
[![Github - Marius][github-marius-shield]][github-marius-url]
[![Github - Razvan][github-razvan-shield]][github-razvan-url]

 -->

<!-- PROJECT LOGO -->
<br />
<p align="center">
  <a href="https://github.com/bogdaniordan/travely-bogdaniordan">
    <img src="https://images-platform.99static.com//JDXR0rEaoLvLvb7xMrFzXCrOs6c=/39x199:539x699/fit-in/500x500/99designs-contests-attachments/100/100842/attachment_100842541" alt="Logo" width="300">
  </a>

  <h3 align="center">Travely</h3>

  <p align="center">
    An awesome booking app for travelers and hosts.
    <br />
    <a href="https://github.com/bogdaniordan/travely-bogdaniordan"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/bogdaniordan/travely-bogdaniordan">View Demo</a>
    ·
    <a href="https://github.com/bogdaniordan/travely-bogdaniordan/issues">Report Bug</a>
    ·
    <a href="https://github.com/bogdaniordan/travely-bogdaniordan/issues">Request Feature</a>
  </p>



<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgements">Acknowledgements</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

This is a useful booking app made for people who want to reserve accommodations or rent a car, and for hosts who want to bring a tenant for their property. The app also has a community feature.

Here's why:
* Customers can browse and book various accommodation hosted by others.
* Customers can manage their bookings, reschedule, rent a car, ask questions etc.
* There is a community feature where customers, can send friend requests, chat or add different posts.
* Hosts can add their bookings, manage them and answer questions from customers.
* Hosts can hire cleaners to keep their accommodations flawless.

### Built With

#### Backend
* [Spring Boot (WebSocket/JPA)](https://spring.io/projects/spring-boot)
* [Lombok](https://projectlombok.org/)
* [PostgreSQL](https://www.postgresql.org/docs/13/app-psql.html)

#### Frontend
* [React](https://reactjs.org/)
* [React-Bootstrap](https://react-bootstrap.github.io/)
* [npm](https://www.npmjs.com/)

#### Version control
* [Github](https://www.gtihub.com/)

#### Project Management
* [Trello](https://www.atlassian.com/software/jira?&aceid=&adposition=&adgroup=89541897982&campaign=9124878150&creative=415542514747&device=c&keyword=jira&matchtype=e&network=g&placement=&ds_kids=p51242161283&ds_e=GOOGLE&ds_eid=700000001558501&ds_e1=GOOGLE&gclid=Cj0KCQiAnKeCBhDPARIsAFDTLTIUjm6m9LQssN_d15V_dYNqPiWaS_df09mdcnHPj-QkqTKrZfAjB6kaAhdEEALw_wcB&gclsrc=aw.ds)



<!-- GETTING STARTED -->
## Getting Started

This application can be tested by installing all prerequisites, clone both the back end and the client app, running them and enjoy!

### Prerequisites

All prerequisites must be installed, accordingly to the technologies used in this project, for example:
* npm
  ```sh
  npm install npm@latest -g
  ```

### Installation

* Backend

1. Clone the repo
   ```sh
   git clone https://github.com/bogdaniordan/travely-backend
   ```
2. Run the server

* Frontend

1. Clone the customer repo and host repo
   ```sh
   git clone https://github.com/bogdaniordan/travely-frontend
   git clone https://github.com/bogdaniordan/travely-host-frontend
   ```
2. Install NPM packages
   ```sh
   npm install
   ```
3. Run the app
   ```
    npm start
   ```

<!-- USAGE EXAMPLES -->
## Usage

Further I will shortly name, describe and visualize some main features of the app.
### Customer application
* Customers can create an account, browse, view and reserve various accommodations.

![Screenshot from 2021-10-21 11-52-02](https://user-images.githubusercontent.com/72221647/138472638-e4d9fc17-480d-410b-be05-faee82842670.png)
![Screenshot from 2021-10-21 12-32-23](https://user-images.githubusercontent.com/72221647/138472677-38e0e3a6-fefc-486f-a791-44531202b7d1.png)
![Screenshot from 2021-10-21 12-32-41](https://user-images.githubusercontent.com/72221647/138472709-83b1c1f8-23c7-4141-8485-1ee718c59ad4.png)

* Customer can browse/edit his profile, manage his bookings 

![Screenshot from 2021-10-21 12-32-56](https://user-images.githubusercontent.com/72221647/138472959-97d83070-0a05-4323-b43b-b25ca9921bb4.png)

* Customers can seach and rent a car for a determined period of time.

![Screenshot from 2021-10-21 12-33-08](https://user-images.githubusercontent.com/72221647/138473048-5ad7bbed-a358-4ca1-a60c-0cdd6c7972b9.png)

* There is a community feature where users can make new friends, make posts and chat privately.

![Screenshot from 2021-10-22 17-32-38](https://user-images.githubusercontent.com/72221647/138473289-ac423fd8-5256-45ea-9a5d-1e6ac5e27b17.png)
![Screenshot from 2021-10-22 17-32-53](https://user-images.githubusercontent.com/72221647/138473354-0aab61de-3125-41b9-a7ba-b3b6a2c89269.png)

### Host application

* Hosts can add new accommodations or manage their bookings.

![Screenshot from 2021-10-21 12-37-44](https://user-images.githubusercontent.com/72221647/138473752-28941616-bd91-4684-929a-73febe205ba3.png)
![Screenshot from 2021-10-21 12-37-56](https://user-images.githubusercontent.com/72221647/138474007-78f584ac-be14-440b-bcdf-cde7d1c92fd5.png)

* Hosts can answer and resolve questions received from their customers.

![Screenshot from 2021-10-21 12-38-40](https://user-images.githubusercontent.com/72221647/138473987-68a6cea0-d451-40ac-8e3f-5d756da24f55.png)

<!-- ROADMAP -->
## Roadmap

The project development took place through 8 Agile iterations, each iteration taking 5 days. A short complete roadmap bellow:

![agile-logo](https://user-images.githubusercontent.com/72221647/138440913-f67be820-c3a8-46d2-a35c-1f847acb2c48.png)

* Sprint 1: Created core backend objects, services and controllers 
* Sprint 2: Started building the UI for customer app
* Sprint 3: Started styling the UI for customer app
* Sprint 4: Connected the UI with the backend and built more functionalities
* Sprint 5: Created the host app frontend
* Sprint 6: Built more functionalities for each app (customer and host)
* Sprint 7: Added landing pages, aws image storage and UI styling
* Sprint 8: Refactored code, fixed various bugs, added CSS styling


<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to be learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request


<!-- CONTACT -->
## Contact

Bogdan Iordan - [@My Github](https://github.com/bogdaniordan) [@My LinkedIn](https://www.linkedin.com/in/bogdan-iordan/) - bogdan.iordan47@gmail.com


<!-- ACKNOWLEDGEMENTS -->
## Acknowledgements
* [Codecool Romania :thumbsup:](https://codecool.com/ro/)
* [React Documentation](https://reactjs.org/)
* [Spring Documentation](https://docs.spring.io/)


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/badge/Contributers-2-brightgreen
[contributors-url]: https://github.com/marius-ceobanu/Poke-Battlez-Frontend/graphs/contributors
[forks-shield]: https://img.shields.io/badge/Forks-0-blue
[forks-url]: https://github.com/marius-ceobanu/Poke-Battlez-Frontend/network/members
[stars-shield]: https://img.shields.io/badge/Stars-2-blue
[stars-url]: https://github.com/marius-ceobanu/Poke-Battlez-Frontend/stargazers
[issues-shield]: https://img.shields.io/github/issues/marius-ceobanu/Poke-Battlez-Frontend
[issues-url]: https://github.com/marius-ceobanu/Poke-Battlez-Frontend/issues
[linkedin-shield]: https://img.shields.io/twitter/url?label=Linkedin%20-%20Marius&logo=LINKEDIN&style=social&url=https%3A%2F%2Fwww.linkedin.com%2Fin%2Fmarius-ciprian-ceobanu-3431157b
[linkedin-marius-url]: https://www.linkedin.com/in/marius-ciprian-ceobanu-3431157b
[github-marius-shield]: https://img.shields.io/twitter/url?label=GitHub%20-%20Marius&logo=Github&style=social&url=https%3A%2F%2Fgithub.com%2Fmarius-ceobanu
[github-marius-url]: https://github.com/marius-ceobanu
[github-razvan-shield]: https://img.shields.io/twitter/url?label=GitHub%20-%20Razvan&logo=Github&style=social&url=https%3A%2F%2Fgithub.com%2Frgrigore
[github-razvan-url]: https://github.com/rgrigore
[chat-png]: doc_images/chat.png
[register-png]: doc_images/register.png
[login-png]: doc_images/login.png
[login-gif]: doc_images/login.gif
[PM-gif]: doc_images/PM.gif
[team-gif]: doc_images/team.gif
[challenge-gif]: doc_images/challenge.gif
[battle-gif]: doc_images/battle.gif
[agile]: doc_images/agile-logo.png
