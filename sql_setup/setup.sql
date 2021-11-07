/*
 * Loyalty Programs and Tiers
 */
CREATE TABLE LoyaltyPrograms (
	id integer GENERATED ALWAYS AS IDENTITY,
    pName varchar(255),
    pCode varchar(255),
    isTiered varchar(1),
    bId integer,
    constraint pk_id primary key (id),
    constraint fk_bId foreign key bId references Brands (id)
);

-- CREATE TABLE RegularPrograms (
--     id int,
--     constraint pk_id primary key (id)  
-- );

-- CREATE TABLE TieredPrograms (
--     id int,
--     constraint pk_id primary key (id)
-- );


CREATE TABLE Tiers (
    pId integer,
    tnum integer,
    tname varchar(255),
    multiplier float(3),
    threshold integer,
    constraint fk_pId foreign key pId references LoyaltyPrograms (id),
    constraint pk_tier primary key (pId, tnum),
    constraint valid_tier check(tnum >= 0 and tnum <= 2),
    constraint valid_multiplier check(multiplier > 0),
    constraint valid_threshold check(threshold >= 0)
);

/*
 * Brands
 */
CREATE TABLE Brands (
    id integer,
    bname varchar(255),
    baddress varchar(255),
    username varchar(255),
    pass varchar(255),
    joinDate date,
    constraint pk_id primary key (id)
);

/*
 * Customers and their Wallets
 */
CREATE TABLE Customers (
    id integer,
    cname varchar(255),
    phoneNumber varchar(15),
    caddress varchar(255),
    username varchar(255),
    pass varchar(255),
    constraint pk_id primary key (id) 
);

CREATE TABLE Wallets (
    id integer,
    constraint pk_id primary key (id)
);

CREATE TABLE CustomerWallets (
    cId integer UNIQUE,
    wId integer UNIQUE,
    constraint fk_cId foreign key cId references Customers (id),
    constraint fk_wId foreign key wId references Wallets (id),
    constraint pk_wallet primary key (cId, wId)
);

CREATE TABLE WalletParticipation (
    wId integer,
    pId integer,
    points integer,
    tierNumber integer, -- A participation in a regular program will have a null tierNumber
    constraint pk_participation primary key (wId, pId),
    constraint fk_wId foreign key wId references Wallets (id),
    constraint fk_pId foreign key pId references LoyaltyPrograms (id)
);

/*
 * Reward Earning
 */
CREATE TABLE ActivityCategories (
    acId varchar(255),
    acName varchar(255),
    constraint pk_id primary key (acId)
);

CREATE TABLE ProgramActivities (
	pId integer,
    acId varchar(255),
    constraint pk_id primary key (pId, acId)
);

CREATE TABLE RewardEarningRules (
    pId integer,
    ruleVersion integer(3),
    ruleCode varchar(6),
    points integer,
    acId integer,
    constraint pk_re primary key (pId, ruleVersion, ruleCode),
    constraint fk_pId foreign key pId references LoyaltyPrograms (id),
    constraint fk_ac foreign key acId references ActivityCategories (acId)
);

CREATE TABLE ActivityInstances (
    id integer,
    instanceDate date,
    relevantInfo varchar(1000),
    pId integer,
    ruleVersion integer(3),
    ruleCode varchar(6),
    wId integer NOT NULL,
    constraint pk_id primary key (id),
    constraint fk_re foreign key (pId, ruleVersion, ruleCode) references RewardEarningRules (pId, ruleVersion, ruleCode)
    constraint fk_wId foreign key wId references Wallets (id)
);

/*
 * Reward Redeeming
 */
CREATE TABLE Rewards (
    rId varchar(255),
    rName varchar(255),
    constraint pk_id primary key (rId)
);

CREATE TABLE ProgramRewards (
	pId integer,
    rId varchar(255),
    constraint pk_id primary key (pId, rId)
);

CREATE TABLE GiftCards (
    id integer,
    pId integer,
    wId integer,
    cardValue float,
    constraint pk_id primary key (id),
    constraint fk_pId foreign key pId references LoyaltyPrograms (id),
    constraint fk_wId foreign key wId references Wallets (id),
);

CREATE TABLE RewardRedeemingRules (
    pId integer,
    ruleVersion integer(3),
    ruleCode varchar(6),
    points integer,
    rId integer,
    quantity integer,
    constraint pk_rr primary key (pId, ruleVersion, ruleCode),
    constraint fk_pId foreign key pId references LoyaltyPrograms (id),
    constraint fk_reward foreign key rId references Rewards (rId)
);