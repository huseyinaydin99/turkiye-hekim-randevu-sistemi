🐳 Docker nedir?
Docker, uygulamaları "container" denilen izole ortamlarda çalıştırmaya yarayan bir platformdur. Uygulama bağımlılıklarıyla birlikte paketlenir. Böylece "bende çalıştı ama sende çalışmıyor" gibi dertler tarih olur. 🔥

🤝 Peki neden Docker?
💡 Hızlı kurulum
💡 Yalıtılmış ortam
💡 Konfigürasyon tekrar edilebilirliği
💡 Projeyi başka ortama taşıma kolaylığı (CI/CD dostu)

🔧 Docker Compose nedir?
Birden fazla servisi tek YAML dosyasında tanımlayıp tek komutla ayağa kaldırmamı sağlar. PostgreSQL + pgAdmin + uygulama backend gibi birçok bileşeni birleştirebilirim.

✔️ Şu anda docker run ile manuel kurdum ama ileride docker-compose.yml dosyası ile daha gelişmiş yapıyı kurmak için temel attım.
Yani şimdilik temeli attım, binayı dikmek docker-compose'la olacak! 🏗️💥

docker pull postgres:15 //1️⃣ PostgreSQL Image'ını Çek

docker run --name thrs_db ^
 -e POSTGRES_DB=thrs_db ^
 -e POSTGRES_USER=root ^
 -e POSTGRES_PASSWORD=toor ^
 -p 5432:5432 ^
 -v thrs_pgdata:/var/lib/postgresql/data ^
 -d postgres:15 //2️⃣ PostgreSQL Container'ını Başlat

docker exec -it thrs_db psql -U root -d thrs_db //3️⃣ PostgreSQL Container’ına Bağlan:

docker exec -it thrs_db psql -U root -d postgres; //postgres üzerinden bağlanırsak diğerini silebiliriz. thrs üzerinden bağlansaydık bağlı olduğumuz için silemeyecektik.
drop database thrs_db;
create database thrs_db;

Diğer komutlar:
docker ps                     :: Çalışan container'ları listele
docker stop thrs_db           :: Container'ı durdur
docker start thrs_db          :: Container'ı başlat
docker restart thrs_db        :: Container'ı yeniden başlat
docker rm thrs_db             :: Container'ı sil (durdurulduktan sonra)