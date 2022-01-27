create table movies (
    id int not null auto_increment,
    name varchar(45) not null,
    duration time not null,
    primary key (id));

create table showtimes (
    id int not null auto_increment,
    movie_id int not null,
    time time not null,
    price decimal(15,2) not null,
    primary key (id),
    constraint fk_movies_id
        foreign key (movie_id)
        references movies (id));

create table tickets (
    id int not null auto_increment,
    showtime_id int not null,
    primary key (id),
    constraint fk_showtime_id
        foreign key (showtime_id)
        references showtimes (id));

insert into movies(name, duration) values
    ('scream 5', '1:30'),
    ('sing 2', '1:30'),
    ('spiderman: no way home', '2:00'),
    ('the kings daughter', '1:00');

insert into showtimes(movie_id, time, price) values
    (1, '9:00', 100),
    (2, '11:00', 200),
    (4, '12:00', 200),
    (3, '14:00', 500),
    (1, '16:00', 400),
    (2, '17:10', 300),
    (3, '19:00', 500),
    (4, '22:00', 100);

insert into tickets(showtime_id) values
    (1),
    (2),
    (3),
    (6),
    (8),
    (1),
    (6),
    (2),
    (4);

//task1
select
    m1.name movie1, s1.time time1, m1.duration duration1,
    m2.name name2, s2.time time2, m2.duration duration2
from showtimes s1
join movies m1 on s1.movie_id = m1.id
join showtimes s2 on time2 - time1 < duration1 and time2 - time1 > 0
join movies m2 on s2.movie_id = m2.id
order by time1 + duration1 - time2;

//task2
select
    m1.name movie, s1.time time1, m1.duration duration,
    s2.time time2, s2.time - s1.time - m1.duration gap
from showtimes s1
join movies m1 on s1.movie_id = m1.id
join showtimes s2 on time2 - time1 - duration > '0:30'
where (select count(s3.id) from showtimes s3 where s3.time > time1 and s3.time < time2) = 0
order by gap;

//task3
select
    m.name, count(t.id) tickets_sold, count(t.id)/showtimes_per_movie.count,
    sum(s.price) box_office
from showtimes s
join movies m on s.movie_id = m.id
join tickets t on t.showtime_id = s.id
join
    (select s1.movie_id movie_id, count(s1.movie_id) count from showtimes s1
    group by s1.movie_id) showtimes_per_movie
on showtimes_per_movie.movie_id = s.movie_id
group by m.name
order by box_office desc;

//task4
select count(t.id) spectators, sum(s.price) box_office,
    case
        when s.time >= '9:00' and s.time < '15:00' then '9:00-15:00'
        when s.time >= '15:00' and s.time < '18:00' then '15:00-18:00'
        when s.time >= '18:00' and s.time < '24:00' then '18:00-00:00'
    end time_range
from showtimes s
join tickets t on t.showtime_id = s.id
group by time_range;