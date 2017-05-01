doubleMe x = x + x

-- doubleUs x y = x * 2 + y * 2
doubleUs x y = doubleMe x + doubleMe y

doubleSmallNumber x = if x > 100
	then x
	else x * 2

doubleSmallNumber' x = (if x > 100 then x else x*2) + 1

cananO'Brien = "It's a me, Conan O'Brien"


