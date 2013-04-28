alter table game add column mail_prohibition boolean;

alter table game alter column mail_prohibition set default false;

create table prereg_notifications (
  user_id int not null,
  game_id int not null,
  notify_date TIMESTAMP not null,

  foreign key (user_id) references hraj_user(id) ON UPDATE CASCADE ON DELETE RESTRICT,
  foreign key (game_id) references game(id) ON UPDATE CASCADE ON DELETE RESTRICT,
  primary key (user_id, game_id)
);