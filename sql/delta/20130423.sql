alter table user_attended_game add column automatic boolean;

create sequence hraj_user_attended_game_seq;

alter table user_attended_game add column variable_symbol text;

Alter table game add column action text;