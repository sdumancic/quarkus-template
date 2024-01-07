USE master
GO

IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'desk_reservation_dev')
    BEGIN
        create database desk_reservation_dev collate Croatian_100_CI_AI_KS_SC_UTF8;
    END;
GO

USE desk_reservation_dev;
GO
