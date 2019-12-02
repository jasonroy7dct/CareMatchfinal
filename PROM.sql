

SELECT * FROM PROMZipMappingBirthArea
ORDER BY ZipTypeCode


select a.CodeType ZipType,a.CodeNo ZipTypeCode,b.CodeType BirthAreaType,b.CodeNo BirthAreaTypeCode, a.CodeName 'Area'
from (select * from PROMCodes where CodeType = '2') a
inner join 
(select * from PROMCodes where CodeType = '3075') b ON Ltrim(a.CodeName) = Ltrim(b.CodeName)
ORDER BY ZipTypeCode


select ZipType,ZipTypeCode,BirthAreaType, BirthAreaTypeCode, ZipMappingBirthAreaId from PROMZipMappingBirthArea
order by ZipTypeCode

SELECT a.CodeType ZipType,a.CodeNo ZipTypeCode,b.CodeType BirthAreaType,b.CodeNo BirthAreaTypeCode, a.CodeName as BirthArea
from (SELECT * from PROMCodes where CodeType = '2') a
inner join 
(SELECT * from PROMCodes where CodeType = '3075') b ON Ltrim(a.CodeName) = Ltrim(b.CodeName)
order by ZipTypeCode



select * From EHRMReportType where ReportTypeId = 10040002