
create table public.user_attended_game (
  user_id int4 not null,
  game_id int4 not null,
  added timestamp default now(),
  substitute bool,
  primary key (user_id, game_id),
  foreign key user_attended_game_game_id_fkey (game_id) references game(id),
  foreign key user_attended_game_user_id_fkey (user_id) references hraj_user(id)
);