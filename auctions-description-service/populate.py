from faker import Faker

faker = Faker(locale='pt_PT')
N = 10000
#print("auctionId, reservePrice, description, name, startingPrice, beginDate, duration, images, mainImage, category_1, category_2, sellerId")
for i in range(N):
  auction_id = str(faker.uuid4())
  reserve_price = faker.random_number(3)
  description = faker.paragraph()
  name = faker.pystr(min_chars=3, max_chars=20)
  starting_price = str(faker.random_int(min=0, max=reserve_price))
  begin_date = faker.iso8601()
  duration = f"0{faker.random_int(min=0, max=9)}:00:00.0"
  images = "[]"
  main_image = "http://example.com"
  category_1 = "Calçado"
  category_2 = "Vestuário"
  seller_id = str(faker.uuid4())
  print(f"{auction_id},{reserve_price},{description},{name},{starting_price},{begin_date},{duration},{images},{main_image},{category_1},{category_2},{seller_id}")
