alter table hraj_user add column role integer;
alter table hraj_user add column accountant boolean;
alter table hraj_user add column place_finder boolean;
alter table hraj_user add column scheduler boolean;

update hraj_user set accountant = false;
update hraj_user set place_finder = false;
update hraj_user set scheduler = false;
update hraj_user set role = 'USER';

drop table accountant;
drop table administrator;
drop table authorized_editor;
drop table places_finder;
drop table scheduler;

