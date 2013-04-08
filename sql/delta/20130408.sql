Alter table game add column confirmed boolean;
Alter table game add column replacements_text text;
Alter table game add column ordinary_player_text text;


create table user_is_editor (
  user_id int not null,
  game_id int not null,

  foreign key (user_id) references hraj_user(id) ON UPDATE CASCADE ON DELETE RESTRICT,
  foreign key (game_id) references game(id) ON UPDATE CASCADE ON DELETE RESTRICT,

  primary key (user_id, game_id)
);

alter table game add column registration_started timestamp;