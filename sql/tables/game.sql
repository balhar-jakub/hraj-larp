create sequence hraj_game_id_seq;

create table game (
  id int unique,
  name text not null,
  date TIMESTAMP not null,
  anotation text not null,
  author text not null,
  image text not null,
  added_by int not null,
  men_role int,
  women_role int,
  both_role int,

  short_text text,
  place text,
  info text,
  about_game text,
  web text,
  larp_db text,

  FOREIGN KEY added_by references hraj_user(id),
  PRIMARY KEY id
);

ALTER TABLE game
ALTER COLUMN id
SET DEFAULT NEXTVAL('hraj_game_id_seq');