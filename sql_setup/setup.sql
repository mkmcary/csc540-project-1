CREATE TABLE Brands (
    id int,
    bname varchar(255),
    baddress varchar(255),
    username varchar(255),
    pass varchar(255),
    joinDate date,
    constraint pk_id primary key (id)
);
/* ADD FOREIGN KEY FOR THE LOYALTY PROGRAM */

CREATE TABLE RegularPrograms (
    id int,
    constraint pk_id primary key (id)  
);

CREATE TABLE TieredPrograms (
    id int,
    constraint pk_id primary key (id)
);

CREATE TABLE Tiers (
    pid int,
    tnum int,
    tname varchar(255),
    multiplier float(3),
    threshold int,
    constraint fk_pid foreign key pid references TieredPrograms (id)
    constraint pk_tier primary key (pid, tnum)
);

CREATE TABLE Customers (
    id int,
    cname varchar(255),
    phoneNumber varchar(15),
    caddress varchar(255),
    username varchar(255),
    pass varchar(255),
    constraint pk_id primary key (id) 
)

CREATE TABLE Wallets (
    id int,
    constraint pk_id primary key (id)
)

CREATE TABLE CustomerWallets (
    cid int UNIQUE,
    wid int UNIQUE,
    constraint fk_cid foreign key cid references Customers (id),
    constraint fk_wid foreign key wid references Wallets (id),
    constraint pk_wallet primary key (cid, wid)
);