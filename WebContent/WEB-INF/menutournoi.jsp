 <nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
  <a class="navbar-brand" href="#">Menu</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarsExampleDefault">
    <ul class="navbar-nav mr-auto">
      
      
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="dropdown01" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Ajouter</a>
        <div class="dropdown-menu" aria-labelledby="dropdown01">
          <a class="dropdown-item" href="/AppliJoueurs/ajouterjoueur">Ajouter joueur</a>
          <a class="dropdown-item" href="/AppliJoueurs/ajoutertournoi">Ajouter tournoi</a>
          <a class="dropdown-item" href="#">Ajouter match</a>
        </div>
      </li>
      
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="dropdown01" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Consulter</a>
        <div class="dropdown-menu" aria-labelledby="dropdown01">
          <a class="dropdown-item" href="/AppliJoueurs/listjoueur">Liste des joueurs</a>
          <a class="dropdown-item" href="/AppliJoueurs/listtournoi">Liste des tournois</a>
          <a class="dropdown-item" href="#">Liste des matchs</a>
        </div>
      </li>
	  
	  
	  <li class="nav-item">
	  <form class="form-inline my-2 my-lg-0" action="listjoueur" method="post">
        <button class="btn btn-secondary my-2 my-sm-0" type="submit" name="action1" value="Deconnexion">Deconnexion</button>
	  </form>
      </li>
      
    </ul>
    <form style="margin-right:5px;" class="form-inline my-2 my-lg-0" action="listtournoi" method="post">
      <input class="form-control mr-sm-2" type="text" placeholder="Search" aria-label="Search" name="search">
      <button class="btn btn-secondary my-2 my-sm-0" type="submit" name="action1" value="Rechercher">Search</button>
    </form>
  </div>
</nav>