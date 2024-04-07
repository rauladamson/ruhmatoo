## Objektorienteeritud programmeerimise rühmatöö: Kollaboratiivne ajaplaneerimise programm
## Kood: LTAT.03.003


## Liikmed:
- Marielin Kepp
- Martin Jaani
- Raul Adamson


## Tutvustus
Meie projektiks on ajaplaneerimise programm, mis võimaldab kasutajatel jagada oma kalendreid ja sündmusi teiste kasutajatega, et teha muudatusi koos. 

##  Funktsionaalsuste nimekiri
- **Kasutajate registreerimine ja sisselogimine:**
  - Kasutajatel peaks olema võimalus luua kontosid ja sisse logida.
  - Sisselogimise funktsioon peaks kontrollima kasutajate autentsust.

- **Kasutaja profiili haldamine:**
  - Kasutajad saavad oma profiili hallata, sealhulgas profiilipilti, kasutajanime ja parooli muuta.

- **Sündmuste lisamine:**
  - Kasutajad saavad luua uusi sündmusi kalendrisse.
  - Iga sündmus peaks sisaldama pealkirja, kirjeldust, asukohta, kuupäeva ja kellaaega.

- **Kalendri kuvamine:**
  - Rakendus peaks võimaldama kasutajatel vaadata kalendrit erinevate vaadetega (päev, nädal, kuu).
  - Kasutajad peaksid nägema nii oma isiklikke sündmusi kui ka teiste kasutajate jagatud sündmusi.

- **Sündmuste jagamine:**
  - Kasutajad peaksid saama jagada oma sündmusi teiste kasutajatega.
  - Jagatud sündmused peaksid olema nähtavad ja muudetavad vastavalt kasutajaõigustele.

- **Sündmuste muutmine ja kustutamine:**
  - Kasutajad peaksid saama muuta ja kustutada oma loodud sündmusi.
  - Jagatud sündmusi peaksid saama muuta ja kustutada ainult vastavate õigustega kasutajad.

- **Sündmuste otsimine:**
  - Rakendus peaks võimaldama kasutajatel otsida sündmusi kuupäeva, pealkirja või asukoha järgi.

- **Sündmuste meeldetuletused:**
  - Rakendus peaks toetama meeldetuletuste seadmist sündmuste kohta.

- **Andmebaasi haldamine:**
  - Sündmused ja kasutajate andmed peaksid olema hoiustatud andmebaasis serveris.
  - Andmebaasi haldus peaks võimaldama sündmuste ja kasutajate lisamist, muutmist ja kustutamist.

- **Andmete sünkroniseerimine:**
  - Kasutajate loodud sündmused peaksid olema sünkroonitud serveriga ja teiste kasutajatega.
  - Andmete sünkroniseerimine peaks toimuma automaatselt, et tagada kõigi kasutajate kalendrite ajakohasus.

- **Turvalisus:**
  - Rakendus peaks kasutama turvalist autentimist ja andmete edastamist krüpteeritud kujul.
  - Kasutajate privaatsust tuleks kaitsta, tagades, et kasutajatele kättesaadavad andmed piirduksid vaid nende õigustega.

- **Kasutajatugi ja abi:**
  - Rakendus peaks pakkuma kasutajatoe võimalust, sealhulgas küsimuste esitamist ja probleemide raporteerimist.
  - Abi- ja juhenddokumentatsioon peaks olema kättesaadav kasutajatele, et aidata neil rakendusega tutvuda ja seda kasutada.

- **Jagatud kalendrite loomine ja haldamine:**
  - Kasutajad peaksid saama luua jagatud kalendreid, mis on kättesaadavad mitmele kasutajale.
  - Jagatud kalendrid võimaldavad mitmel kasutajal koos töötada ja muuta ühte kalendrit.

- **Kasutajate lisamine ja eemaldamine jagatud kalendrisse:**
  - Kalendri omanik peaks saama lisada teisi kasutajaid jagatud kalendrisse ning neile vastavaid õigusi määrata (lugemine, kirjutamine, muutmine).
  - Kasutajad peaksid saama eemaldada teisi kasutajaid jagatud kalendrist vastavaid õigusi.

- **Kalendri jagamise õiguste haldamine:**
  - Kalendri omanik peaks saama määrata, millised õigused on teistel kasutajatel jagatud kalendris (nt lugemine, kirjutamine, muutmine).
  - Kasutajatele tuleks anda võimalus jagatud kalendri õiguste haldamiseks vastavalt nende rollile kalendri kasutamisel.

- **Kalendri sünkroniseerimine:**
  - Jagatud kalendrid peaksid olema sünkroonitud kõigi kasutajate vahel, tagades, et muudatused kajastuksid kõigil kasutajatel reaalajas.
  - Muudatuste tegemisel kalendris peaks kõigile kasutajatele kuvama ajakohastatud versiooni jagatud kalendrist.

- **Muudatuste jälgimine ja ajalugu:**
  - Rakendus peaks võimaldama jälgida muudatusi jagatud kalendris, sealhulgas kes ja millal muudatusi tegi.
  - Kasutajad peaksid saama vaadata kalendri muudatuste ajalugu, et jälgida, kuidas kalendri sisu on aja jooksul muutunud.
