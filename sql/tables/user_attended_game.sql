create table user_attended_game (
  user_id int not null,
  game_id int not null,
  added timestamp DEFAULT CURRENT_TIMESTAMP,

  foreign key (user_id) references hraj_user(id) ON UPDATE CASCADE ON DELETE RESTRICT,
  foreign key (game_id) references game(id) ON UPDATE CASCADE ON DELETE RESTRICT,

  primary key (user_id, game_id)
)