#set page(
  paper: "us-letter",
  margin: (top: 1in, bottom: 0.75in, x: 0.75in),
  columns: 2,
  footer: align(center)[Joshua, Judges, and Ruth — March 28, 2026],
)
#set text(font: "Libertinus Serif", size: 10pt)
#set par(justify: true)
#show footnote.entry: set text(size: 9pt)

// Built-in highlight color — the default fill for divine names (matching DOCX)
#let divineColor = rgb(255, 255, 0)
#let men = rgb(153, 204, 255)
#let places = rgb(153, 255, 153)
#let women = rgb(255, 153, 255)
#let people-groups = rgb(204, 204, 204)
#let divine = rgb(255, 255, 0)
#let numbers = rgb(255, 182, 108)
#let other = rgb(46, 230, 217)
#let myhl(color, body) = highlight(fill: color, body)
#let versenum(n) = box(
    fill: rgb("404040"),
    inset: (x: 3pt, y: 1pt),
    radius: 1pt,
    text(fill: white, weight: "bold", font: "Libertinus Serif")[#n],
)
#let chapter-heading(label) = heading(
    level: 1, outlined: false,
    text(font: "Libertinus Serif", size: 14pt, weight: "bold")[#label],
)
#let section-heading(label) = heading(
    level: 2, outlined: false,
    text(font: "Libertinus Serif", size: 12pt, weight: "bold")[#label],
)
#let vin = h(2em)

  
#chapter-heading[Joshua 1]


#section-heading[God Commissions Joshua]


#versenum(1) After the death of #myhl(men)[Moses] the servant of the #myhl(divine)[#smallcaps[Lord]], the #myhl(divine)[#smallcaps[Lord]] said to #myhl(men)[Joshua] the son of #myhl(men)[Nun], #myhl(men)[Moses]’ #underline[assistant], 
#versenum(2) “#myhl(men)[Moses] my servant is dead. Now therefore arise, go over this #myhl(places)[Jordan], you and all this people, into the land that I am giving to them, to the #myhl(people-groups)[people of Israel]. 
#versenum(3) Every place that the #underline[sole] of your foot will #underline[tread] upon I have given to you, just as I promised to #myhl(men)[Moses]. 
#versenum(4) From the wilderness and this #myhl(places)[Lebanon] as far as the great river, the river #myhl(places)[Euphrates], all the land of the #myhl(other)[Hittites] to the #myhl(places)[Great Sea] toward the going down of the sun shall be your territory. 
#versenum(5) No man shall be able to stand before you all the days of your life. Just as I was with #myhl(men)[Moses], so I will be with you. I will not leave you or forsake you. 
#versenum(6) Be strong and courageous, for you shall cause this people to inherit the land that I swore to their fathers to give them. 
#versenum(7) Only be strong and very courageous, being careful to do according to all the law that #myhl(men)[Moses] my servant commanded you. Do not turn from it to the right hand or to the left, that you may have good success#footnote[Joshua 1:7 Or #emph[may act wisely]] wherever you go. 
#versenum(8) This Book of the Law shall not depart from your mouth, but you shall #underline[meditate] on it day and night, so that you may be careful to do according to all that is written in it. For then you will make your way #underline[prosperous], and then you will have good success. 
#versenum(9) Have I not commanded you? Be strong and courageous. Do not be #underline[frightened], and do not be dismayed, for the #myhl(divine)[#smallcaps[Lord] your God] is with you wherever you go.”


  
#section-heading[Joshua Assumes Command]


#versenum(10) And #myhl(men)[Joshua] commanded the officers of the people, 
#versenum(11) “Pass through the midst of the camp and command the people, ‘Prepare your provisions, for within #myhl(numbers)[three] days you are to pass over this #myhl(places)[Jordan] to go in to take possession of the land that the #myhl(divine)[#smallcaps[Lord] your God] is giving you to possess.’”


  
#versenum(12) And to the #myhl(other)[Reubenites], the #myhl(other)[Gadites], and the #myhl(numbers)[half]-tribe of #myhl(other)[Manasseh] #myhl(men)[Joshua] said, 
#versenum(13) “Remember the word that #myhl(men)[Moses] the servant of the #myhl(divine)[#smallcaps[Lord]] commanded you, saying, ‘The #myhl(divine)[#smallcaps[Lord] your God] is #underline[providing] you a place of rest and will give you this land.’ 
#versenum(14) Your wives, your little ones, and your livestock shall remain in the land that #myhl(men)[Moses] gave you beyond the #myhl(places)[Jordan], but all the men of valor among you shall pass over armed before your brothers and shall help them, 
#versenum(15) until the #myhl(divine)[#smallcaps[Lord]] gives rest to your brothers as he has to you, and they also take possession of the land that the #myhl(divine)[#smallcaps[Lord] your God] is giving them. Then you shall return to the land of your possession and shall possess it, the land that #myhl(men)[Moses] the servant of the #myhl(divine)[#smallcaps[Lord]] gave you beyond the #myhl(places)[Jordan] toward the sunrise.”


  
#versenum(16) And they answered #myhl(men)[Joshua], “All that you have commanded us we will do, and wherever you send us we will go. 
#versenum(17) Just as we obeyed #myhl(men)[Moses] in all things, so we will obey you. Only may the #myhl(divine)[#smallcaps[Lord] your God] be with you, as he was with #myhl(men)[Moses]! 
#versenum(18) Whoever rebels against your commandment and #underline[disobeys] your words, whatever you command him, shall be put to death. Only be strong and courageous.”


  
#chapter-heading[Joshua 2]


#section-heading[Rahab Hides the Spies]


#versenum(1) And #myhl(men)[Joshua] the son of #myhl(men)[Nun] sent#footnote[Joshua 2:1 Or #emph[had sent]] #myhl(numbers)[two] men secretly from #myhl(places)[Shittim] as spies, saying, “Go, view the land, #underline[especially] #myhl(places)[Jericho].” And they went and came into the house of a prostitute whose name was #myhl(women)[Rahab] and lodged there. 
#versenum(2) And it was told to the king of #myhl(places)[Jericho], “Behold, #myhl(people-groups)[men of Israel] have come here tonight to search out the land.” 
#versenum(3) Then the king of #myhl(places)[Jericho] sent to #myhl(women)[Rahab], saying, “Bring out the men who have come to you, who entered your house, for they have come to search out all the land.” 
#versenum(4) But the woman had taken the #myhl(numbers)[two] men and hidden them. And she said, “True, the men came to me, but I did not know where they were from. 
#versenum(5) And when the gate was about to be closed at #underline[dark], the men went out. I do not know where the men went. Pursue them quickly, for you will #underline[overtake] them.” 
#versenum(6) But she had brought them up to the roof and hid them with the #underline[stalks] of flax that she had laid in order on the roof. 
#versenum(7) So the men pursued after them on the way to the #myhl(places)[Jordan] as far as the fords. And the gate was shut as soon as the pursuers had gone out.


  
#versenum(8) Before the men#footnote[Joshua 2:8 Hebrew #emph[they]] lay down, she came up to them on the roof 
#versenum(9) and said to the men, “I know that the #myhl(divine)[#smallcaps[Lord]] has given you the land, and that the fear of you has fallen upon us, and that all the inhabitants of the land melt away before you. 
#versenum(10) For we have heard how the #myhl(divine)[#smallcaps[Lord]] dried up the water of the #myhl(places)[Red Sea] before you when you came out of #myhl(places)[Egypt], and what you did to the #myhl(numbers)[two] kings of the #myhl(other)[Amorites] who were beyond the #myhl(places)[Jordan], to #myhl(men)[Sihon] and #myhl(men)[Og], whom you devoted to destruction.#footnote[Joshua 2:10 That is, set apart (devoted) as an offering to the Lord (for destruction)] 
#versenum(11) And as soon as we heard it, our hearts melted, and there was no spirit left in any man because of you, for the #myhl(divine)[#smallcaps[Lord] your God], he is #myhl(divine)[God] in the heavens above and on the earth #underline[beneath]. 
#versenum(12) Now then, please swear to me by the #myhl(divine)[#smallcaps[Lord]] that, as I have dealt kindly with you, you also will deal kindly with my father’s house, and give me a #underline[sure] sign 
#versenum(13) that you will save alive my father and mother, my brothers and #underline[sisters], and all who belong to them, and deliver our lives from death.” 
#versenum(14) And the men said to her, “Our life for yours even to death! If you do not tell this business of ours, then when the #myhl(divine)[#smallcaps[Lord]] gives us the land we will deal kindly and #underline[faithfully] with you.”


  
#versenum(15) Then she let them down by a #underline[rope] through the window, for her house was built into the city wall, so that she lived in the wall. 
#versenum(16) And she said#footnote[Joshua 2:16 Or #emph[had said]] to them, “Go into the hills, or the pursuers will #underline[encounter] you, and hide there #myhl(numbers)[three] days until the pursuers have returned. Then afterward you may go your way.” 
#versenum(17) The men said to her, “We will be guiltless with respect to this oath of yours that you have made us swear. 
#versenum(18) Behold, when we come into the land, you shall #underline[tie] this scarlet cord in the window through which you let us down, and you shall gather into your house your father and mother, your brothers, and all your father’s household. 
#versenum(19) Then if anyone goes out of the doors of your house into the #underline[street], his blood shall be on his own head, and we shall be guiltless. But if a hand is laid on anyone who is with you in the house, his blood shall be on our head. 
#versenum(20) But if you tell this business of ours, then we shall be guiltless with respect to your oath that you have made us swear.” 
#versenum(21) And she said, “According to your words, so be it.” Then she sent them away, and they departed. And she #underline[tied] the scarlet cord in the window.


  
#versenum(22) They departed and went into the hills and remained there #myhl(numbers)[three] days until the pursuers returned, and the pursuers searched all along the way and found nothing. 
#versenum(23) Then the #myhl(numbers)[two] men returned. They came down from the hills and passed over and came to #myhl(men)[Joshua] the son of #myhl(men)[Nun], and they told him all that had happened to them. 
#versenum(24) And they said to #myhl(men)[Joshua], “Truly the #myhl(divine)[#smallcaps[Lord]] has given all the land into our hands. And also, all the inhabitants of the land melt away because of us.”


  
#chapter-heading[Joshua 3]


#section-heading[Israel Crosses the Jordan]


#versenum(1) Then #myhl(men)[Joshua] rose early in the morning and they set out from #myhl(places)[Shittim]. And they came to the #myhl(places)[Jordan], he and all the #myhl(people-groups)[people of Israel], and lodged there before they passed over. 
#versenum(2) At the end of #myhl(numbers)[three] days the officers went through the camp 
#versenum(3) and commanded the people, “As soon as you see the ark of the covenant of the #myhl(divine)[#smallcaps[Lord] your God] being carried by the Levitical priests, then you shall set out from your place and follow it. 
#versenum(4) Yet there shall be a distance between you and it, about #myhl(numbers)[2,000] #underline[cubits]#footnote[Joshua 3:4 A #emph[cubit] was about 18 inches or 45 centimeters] in length. Do not come near it, in order that you may know the way you shall go, for you have not passed this way before.” 
#versenum(5) Then #myhl(men)[Joshua] said to the people, “Consecrate yourselves, for tomorrow the #myhl(divine)[#smallcaps[Lord]] will do wonders among you.” 
#versenum(6) And #myhl(men)[Joshua] said to the priests, “Take up the ark of the covenant and pass on before the people.” So they took up the ark of the covenant and went before the people.


  
#versenum(7) The #myhl(divine)[#smallcaps[Lord]] said to #myhl(men)[Joshua], “Today I will begin to #underline[exalt] you in the sight of all #myhl(people-groups)[Israel], that they may know that, as I was with #myhl(men)[Moses], so I will be with you. 
#versenum(8) And as for you, command the priests who bear the ark of the covenant, ‘When you come to the brink of the waters of the #myhl(places)[Jordan], you shall stand still in the #myhl(places)[Jordan].’” 
#versenum(9) And #myhl(men)[Joshua] said to the #myhl(people-groups)[people of Israel], “Come here and listen to the words of the #myhl(divine)[#smallcaps[Lord] your God].” 
#versenum(10) And #myhl(men)[Joshua] said, “Here is how you shall know that the living #myhl(divine)[God] is among you and that he will without #underline[fail] drive out from before you the #myhl(other)[Canaanites], the #myhl(other)[Hittites], the #myhl(other)[Hivites], the #myhl(other)[Perizzites], the #myhl(other)[Girgashites], the #myhl(other)[Amorites], and the #myhl(other)[Jebusites]. 
#versenum(11) Behold, the ark of the covenant of the #myhl(divine)[Lord] of all the earth#footnote[Joshua 3:11 Hebrew #emph[the ark of the covenant, the Lord of all the earth]] is passing over before you into the #myhl(places)[Jordan]. 
#versenum(12) Now therefore take #myhl(numbers)[twelve] men from the tribes of #myhl(people-groups)[Israel], from each tribe a man. 
#versenum(13) And when the soles of the feet of the priests bearing the ark of the #myhl(divine)[#smallcaps[Lord]], the #myhl(divine)[Lord] of all the earth, shall rest in the waters of the #myhl(places)[Jordan], the waters of the #myhl(places)[Jordan] shall be cut off from flowing, and the waters coming down from above shall stand in #myhl(numbers)[one] heap.”


  
#versenum(14) So when the people set out from their tents to pass over the #myhl(places)[Jordan] with the priests bearing the ark of the covenant before the people, 
#versenum(15) and as soon as those bearing the ark had come as far as the #myhl(places)[Jordan], and the feet of the priests bearing the ark were #underline[dipped] in the brink of the water (now the #myhl(places)[Jordan] #underline[overflows] all its banks throughout the time of harvest), 
#versenum(16) the waters coming down from above stood and rose up in a heap very far away, at #underline[#myhl(places)[Adam]], the city that is beside #underline[#myhl(places)[Zarethan]], and those flowing down toward the #myhl(places)[Sea of the Arabah], the #myhl(places)[Salt Sea], were completely cut off. And the people passed over opposite #myhl(places)[Jericho]. 
#versenum(17) Now the priests bearing the ark of the covenant of the #myhl(divine)[#smallcaps[Lord]] stood firmly on dry ground in the midst of the #myhl(places)[Jordan], and all #myhl(people-groups)[Israel] was passing over on dry ground until all the nation finished passing over the #myhl(places)[Jordan].


  
#chapter-heading[Joshua 4]


#section-heading[Twelve Memorial Stones from the Jordan]


#versenum(1) When all the nation had finished passing over the #myhl(places)[Jordan], the #myhl(divine)[#smallcaps[Lord]] said to #myhl(men)[Joshua], 
#versenum(2) “Take #myhl(numbers)[twelve] men from the people, from each tribe a man, 
#versenum(3) and command them, saying, ‘Take #myhl(numbers)[twelve] stones from here out of the midst of the #myhl(places)[Jordan], from the very place where the priests’ feet stood firmly, and bring them over with you and lay them down in the place where you lodge tonight.’” 
#versenum(4) Then #myhl(men)[Joshua] called the #myhl(numbers)[twelve] men from the #myhl(people-groups)[people of Israel], whom he had appointed, a man from each tribe. 
#versenum(5) And #myhl(men)[Joshua] said to them, “Pass on before the ark of the #myhl(divine)[#smallcaps[Lord] your God] into the midst of the #myhl(places)[Jordan], and take up each of you a stone upon his shoulder, according to the number of the tribes of the #myhl(people-groups)[people of Israel], 
#versenum(6) that this may be a sign among you. When your children ask in time to come, ‘What do those stones mean to you?’ 
#versenum(7) then you shall tell them that the waters of the #myhl(places)[Jordan] were cut off before the ark of the covenant of the #myhl(divine)[#smallcaps[Lord]]. When it passed over the #myhl(places)[Jordan], the waters of the #myhl(places)[Jordan] were cut off. So these stones shall be to the #myhl(people-groups)[people of Israel] a #underline[memorial] forever.”


  
#versenum(8) And the #myhl(people-groups)[people of Israel] did just as #myhl(men)[Joshua] commanded and took up #myhl(numbers)[twelve] stones out of the midst of the #myhl(places)[Jordan], according to the number of the tribes of the #myhl(people-groups)[people of Israel], just as the #myhl(divine)[#smallcaps[Lord]] told #myhl(men)[Joshua]. And they carried them over with them to the place where they lodged and laid them down#footnote[Joshua 4:8 Or #emph[to rest]] there. 
#versenum(9) And #myhl(men)[Joshua] set up#footnote[Joshua 4:9 Or #emph[Joshua had set up]] #myhl(numbers)[twelve] stones in the midst of the #myhl(places)[Jordan], in the place where the feet of the priests bearing the ark of the covenant had stood; and they are there to this day. 
#versenum(10) For the priests bearing the ark stood in the midst of the #myhl(places)[Jordan] until everything was finished that the #myhl(divine)[#smallcaps[Lord]] commanded #myhl(men)[Joshua] to tell the people, according to all that #myhl(men)[Moses] had commanded #myhl(men)[Joshua].


  The people passed over in #underline[haste]. 
#versenum(11) And when all the people had finished passing over, the ark of the #myhl(divine)[#smallcaps[Lord]] and the priests passed over before the people. 
#versenum(12) The sons of #myhl(other)[Reuben] and the sons of #myhl(other)[Gad] and the #myhl(numbers)[half]-tribe of #myhl(other)[Manasseh] passed over armed before the #myhl(people-groups)[people of Israel], as #myhl(men)[Moses] had told them. 
#versenum(13) About #underline[#myhl(numbers)[40,000]] ready for war passed over before the #myhl(divine)[#smallcaps[Lord]] for battle, to the plains of #myhl(places)[Jericho]. 
#versenum(14) On that day the #myhl(divine)[#smallcaps[Lord]] #underline[exalted] #myhl(men)[Joshua] in the sight of all #myhl(people-groups)[Israel], and they stood in awe of him just as they had stood in awe of #myhl(men)[Moses], all the days of his life.


  
#versenum(15) And the #myhl(divine)[#smallcaps[Lord]] said to #myhl(men)[Joshua], 
#versenum(16) “Command the priests bearing the ark of the #underline[testimony] to come up out of the #myhl(places)[Jordan].” 
#versenum(17) So #myhl(men)[Joshua] commanded the priests, “Come up out of the #myhl(places)[Jordan].” 
#versenum(18) And when the priests bearing the ark of the covenant of the #myhl(divine)[#smallcaps[Lord]] came up from the midst of the #myhl(places)[Jordan], and the soles of the priests’ feet were lifted up on dry ground, the waters of the #myhl(places)[Jordan] returned to their place and #underline[overflowed] all its banks, as before.


  
#versenum(19) The people came up out of the #myhl(places)[Jordan] on the #underline[#myhl(numbers)[tenth]] day of the #myhl(numbers)[first] month, and they encamped at #myhl(places)[Gilgal] on the east border of #myhl(places)[Jericho]. 
#versenum(20) And those #myhl(numbers)[twelve] stones, which they took out of the #myhl(places)[Jordan], #myhl(men)[Joshua] set up at #myhl(places)[Gilgal]. 
#versenum(21) And he said to the #myhl(people-groups)[people of Israel], “When your children ask their fathers in times to come, ‘What do these stones mean?’ 
#versenum(22) then you shall let your children know, ‘#myhl(people-groups)[Israel] passed over this #myhl(places)[Jordan] on dry ground.’ 
#versenum(23) For the #myhl(divine)[#smallcaps[Lord] your God] dried up the waters of the #myhl(places)[Jordan] for you until you passed over, as the #myhl(divine)[#smallcaps[Lord] your God] did to the #myhl(places)[Red Sea], which he dried up for us until we passed over, 
#versenum(24) so that all the peoples of the earth may know that the hand of the #myhl(divine)[#smallcaps[Lord]] is mighty, that you may fear the #myhl(divine)[#smallcaps[Lord] your God] forever.”#footnote[Joshua 4:24 Or #emph[all the days]]


  
#chapter-heading[Joshua 5]


#section-heading[The New Generation Circumcised]


#versenum(1) As soon as all the kings of the #myhl(other)[Amorites] who were beyond the #myhl(places)[Jordan] to the west, and all the kings of the #myhl(other)[Canaanites] who were by the sea, heard that the #myhl(divine)[#smallcaps[Lord]] had dried up the waters of the #myhl(places)[Jordan] for the #myhl(people-groups)[people of Israel] until they had crossed over, their hearts melted and there was no longer any spirit in them because of the #myhl(people-groups)[people of Israel].


  
#versenum(2) At that time the #myhl(divine)[#smallcaps[Lord]] said to #myhl(men)[Joshua], “Make flint knives and #underline[circumcise] the sons of #myhl(people-groups)[Israel] a #myhl(numbers)[second] time.” 
#versenum(3) So #myhl(men)[Joshua] made flint knives and circumcised the sons of #myhl(people-groups)[Israel] at #underline[#myhl(places)[Gibeath-haaraloth]].#footnote[Joshua 5:3 #emph[Gibeath-haaraloth] means #emph[the hill of the foreskins]] 
#versenum(4) And this is the #underline[reason] why #myhl(men)[Joshua] circumcised them: all the #underline[males] of the people who came out of #myhl(places)[Egypt], all the men of war, had died in the wilderness on the way after they had come out of #myhl(places)[Egypt]. 
#versenum(5) Though all the people who came out had been circumcised, yet all the people who were born on the way in the wilderness after they had come out of #myhl(places)[Egypt] had not been circumcised. 
#versenum(6) For the #myhl(people-groups)[people of Israel] walked #myhl(numbers)[forty] years in the wilderness, until all the nation, the men of war who came out of #myhl(places)[Egypt], #underline[perished], because they did not obey the voice of the #myhl(divine)[#smallcaps[Lord]]; the #myhl(divine)[#smallcaps[Lord]] swore to them that he would not let them see the land that the #myhl(divine)[#smallcaps[Lord]] had sworn to their fathers to give to us, a land flowing with milk and honey. 
#versenum(7) So it was their children, whom he raised up in their place, that #myhl(men)[Joshua] circumcised. For they were uncircumcised, because they had not been circumcised on the way.


  
#versenum(8) When the #underline[circumcising] of the whole nation was finished, they remained in their places in the camp until they were #underline[healed]. 
#versenum(9) And the #myhl(divine)[#smallcaps[Lord]] said to #myhl(men)[Joshua], “Today I have #underline[rolled] away the reproach of #myhl(places)[Egypt] from you.” And so the name of that place is called #myhl(places)[Gilgal]#footnote[Joshua 5:9 #emph[Gilgal] sounds like the Hebrew for #emph[to roll]] to this day.


  
#section-heading[First Passover in Canaan]


#versenum(10) While the #myhl(people-groups)[people of Israel] were encamped at #myhl(places)[Gilgal], they kept the #myhl(other)[Passover] on the #underline[#myhl(numbers)[fourteenth]] day of the month in the evening on the plains of #myhl(places)[Jericho]. 
#versenum(11) And the day after the #myhl(other)[Passover], on that very day, they ate of the produce of the land, unleavened cakes and #underline[parched] grain. 
#versenum(12) And the manna ceased the day after they ate of the produce of the land. And there was no longer manna for the #myhl(people-groups)[people of Israel], but they ate of the fruit of the land of #myhl(places)[Canaan] that year.


  
#section-heading[The Commander of the LORD’s Army]


#versenum(13) When #myhl(men)[Joshua] was by #myhl(places)[Jericho], he lifted up his eyes and looked, and behold, a man was standing before him with his drawn sword in his hand. And #myhl(men)[Joshua] went to him and said to him, “Are you for us, or for our #underline[adversaries]?” 
#versenum(14) And he said, “No; but I am the commander of the army of the #myhl(divine)[#smallcaps[Lord]]. Now I have come.” And #myhl(men)[Joshua] fell on his face to the earth and worshiped#footnote[Joshua 5:14 Or #emph[and paid homage]] and said to him, “What does my lord say to his servant?” 
#versenum(15) And the commander of the #myhl(divine)[#smallcaps[Lord]]’s army said to #myhl(men)[Joshua], “Take off your sandals from your feet, for the place where you are standing is holy.” And #myhl(men)[Joshua] did so.


  
#chapter-heading[Joshua 6]


#section-heading[The Fall of Jericho]


#versenum(1) Now #myhl(places)[Jericho] was shut up inside and outside because of the #myhl(people-groups)[people of Israel]. None went out, and none came in. 
#versenum(2) And the #myhl(divine)[#smallcaps[Lord]] said to #myhl(men)[Joshua], “See, I have given #myhl(places)[Jericho] into your hand, with its king and mighty men of valor. 
#versenum(3) You shall march around the city, all the men of war going around the city once. Thus shall you do for #myhl(numbers)[six] days. 
#versenum(4) #myhl(numbers)[Seven] priests shall bear #myhl(numbers)[seven] trumpets of rams’ horns before the ark. On the #myhl(numbers)[seventh] day you shall march around the city #myhl(numbers)[seven] times, and the priests shall blow the trumpets. 
#versenum(5) And when they make a long #underline[blast] with the #underline[ram’s] #underline[horn], when you hear the sound of the trumpet, then all the people shall shout with a great shout, and the wall of the city will fall down flat,#footnote[Joshua 6:5 Hebrew #emph[under itself]; also verse 20] and the people shall go up, everyone straight before him.” 
#versenum(6) So #myhl(men)[Joshua] the son of #myhl(men)[Nun] called the priests and said to them, “Take up the ark of the covenant and let #myhl(numbers)[seven] priests bear #myhl(numbers)[seven] trumpets of rams’ horns before the ark of the #myhl(divine)[#smallcaps[Lord]].” 
#versenum(7) And he said to the people, “Go forward. March around the city and let the armed men pass on before the ark of the #myhl(divine)[#smallcaps[Lord]].”


  
#versenum(8) And just as #myhl(men)[Joshua] had commanded the people, the #myhl(numbers)[seven] priests bearing the #myhl(numbers)[seven] trumpets of rams’ horns before the #myhl(divine)[#smallcaps[Lord]] went forward, blowing the trumpets, with the ark of the covenant of the #myhl(divine)[#smallcaps[Lord]] following them. 
#versenum(9) The armed men were walking before the priests who were blowing the trumpets, and the rear guard was walking after the ark, while the trumpets blew continually. 
#versenum(10) But #myhl(men)[Joshua] commanded the people, “You shall not shout or make your voice heard, neither shall any word go out of your mouth, until the day I tell you to shout. Then you shall shout.” 
#versenum(11) So he #underline[caused] the ark of the #myhl(divine)[#smallcaps[Lord]] to #underline[circle] the city, going about it once. And they came into the camp and spent the night in the camp.


  
#versenum(12) Then #myhl(men)[Joshua] rose early in the morning, and the priests took up the ark of the #myhl(divine)[#smallcaps[Lord]]. 
#versenum(13) And the #myhl(numbers)[seven] priests bearing the #myhl(numbers)[seven] trumpets of rams’ horns before the ark of the #myhl(divine)[#smallcaps[Lord]] walked on, and they blew the trumpets continually. And the armed men were walking before them, and the rear guard was walking after the ark of the #myhl(divine)[#smallcaps[Lord]], while the trumpets blew continually. 
#versenum(14) And the #myhl(numbers)[second] day they marched around the city once, and returned into the camp. So they did for #myhl(numbers)[six] days.


  
#versenum(15) On the #myhl(numbers)[seventh] day they rose early, at the dawn of day, and marched around the city in the same manner #myhl(numbers)[seven] times. It was only on that day that they marched around the city #myhl(numbers)[seven] times. 
#versenum(16) And at the #myhl(numbers)[seventh] time, when the priests had blown the trumpets, #myhl(men)[Joshua] said to the people, “Shout, for the #myhl(divine)[#smallcaps[Lord]] has given you the city. 
#versenum(17) And the city and all that is within it shall be devoted to the #myhl(divine)[#smallcaps[Lord]] for destruction.#footnote[Joshua 6:17 That is, set apart (devoted) as an offering to the Lord (for destruction); also verses 18, 21] Only #myhl(women)[Rahab] the prostitute and all who are with her in her house shall live, because she hid the messengers whom we sent. 
#versenum(18) But you, keep yourselves from the things devoted to destruction, lest when you have devoted them you take any of the devoted things and make #myhl(places)[the camp of Israel] a thing for destruction and bring trouble upon it. 
#versenum(19) But all silver and gold, and every #underline[vessel] of bronze and iron, are holy to the #myhl(divine)[#smallcaps[Lord]]; they shall go into the treasury of the #myhl(divine)[#smallcaps[Lord]].” 
#versenum(20) So the people shouted, and the trumpets were blown. As soon as the people heard the sound of the trumpet, the people shouted a great shout, and the wall fell down flat, so that the people went up into the city, every man straight before him, and they captured the city. 
#versenum(21) Then they devoted all in the city to destruction, both men and women, young and old, oxen, sheep, and donkeys, with the edge of the sword.


  
#versenum(22) But to the #myhl(numbers)[two] men who had spied out the land, #myhl(men)[Joshua] said, “Go into the #underline[prostitute’s] house and bring out from there the woman and all who belong to her, as you swore to her.” 
#versenum(23) So the young men who had been spies went in and brought out #myhl(women)[Rahab] and her father and mother and brothers and all who belonged to her. And they brought all her relatives and put them outside #myhl(places)[the camp of Israel]. 
#versenum(24) And they burned the city with fire, and everything in it. Only the silver and gold, and the vessels of bronze and of iron, they put into the treasury of the house of the #myhl(divine)[#smallcaps[Lord]]. 
#versenum(25) But #myhl(women)[Rahab] the prostitute and her father’s household and all who belonged to her, #myhl(men)[Joshua] saved alive. And she has lived in #myhl(places)[Israel] to this day, because she hid the messengers whom #myhl(men)[Joshua] sent to spy out #myhl(places)[Jericho].


  
#versenum(26) #myhl(men)[Joshua] laid an oath on them at that time, saying, “Cursed before the #myhl(divine)[#smallcaps[Lord]] be the man who rises up and #underline[rebuilds] this city, #myhl(places)[Jericho].


    “At the cost of his firstborn\
    #vin shall he lay its #underline[foundation],\
    and at the cost of his youngest son\
    #vin shall he set up its gates.”\


      
#versenum(27) So the #myhl(divine)[#smallcaps[Lord]] was with #myhl(men)[Joshua], and his #underline[fame] was in all the land.


  
#chapter-heading[Joshua 7]


#section-heading[Israel Defeated at Ai]


#versenum(1) But the #myhl(people-groups)[people of Israel] broke faith in regard to the devoted things, for #myhl(men)[Achan] the son of #myhl(men)[Carmi], son of #myhl(men)[Zabdi], son of #myhl(men)[Zerah], of the tribe of #myhl(other)[Judah], took some of the devoted things. And the anger of the #myhl(divine)[#smallcaps[Lord]] burned against the #myhl(people-groups)[people of Israel].


  
#versenum(2) #myhl(men)[Joshua] sent men from #myhl(places)[Jericho] to #myhl(places)[Ai], which is near #myhl(places)[Beth-aven], east of #myhl(places)[Bethel], and said to them, “Go up and spy out the land.” And the men went up and spied out #myhl(places)[Ai]. 
#versenum(3) And they returned to #myhl(men)[Joshua] and said to him, “Do not have all the people go up, but let about #myhl(numbers)[two] or #myhl(numbers)[three thousand] men go up and attack #myhl(places)[Ai]. Do not make the whole people #underline[toil] up there, for they are #underline[few].” 
#versenum(4) So about #myhl(numbers)[three thousand] men went up there from the people. And they fled before the men of #myhl(places)[Ai], 
#versenum(5) and the men of #myhl(places)[Ai] killed about #underline[#myhl(numbers)[thirty-six]] of their men and chased them before the gate as far as #underline[#myhl(places)[Shebarim]] and struck them at the #underline[descent]. And the hearts of the people melted and became as water.


  
#versenum(6) Then #myhl(men)[Joshua] tore his clothes and fell to the earth on his face before the ark of the #myhl(divine)[#smallcaps[Lord]] until the evening, he and the elders of #myhl(people-groups)[Israel]. And they put #underline[dust] on their heads. 
#versenum(7) And #myhl(men)[Joshua] said, “Alas, O #myhl(divine)[Lord] #myhl(divine)[GOD], why have you brought this people over the #myhl(places)[Jordan] at all, to give us into the hands of the #myhl(other)[Amorites], to destroy us? Would that we had been content to dwell beyond the #myhl(places)[Jordan]! 
#versenum(8) O #myhl(divine)[Lord], what can I say, when #myhl(people-groups)[Israel] has turned their backs before their enemies! 
#versenum(9) For the #myhl(other)[Canaanites] and all the inhabitants of the land will hear of it and will #underline[surround] us and cut off our name from the earth. And what will you do for your great name?”


  
#section-heading[The Sin of Achan]


#versenum(10) The #myhl(divine)[#smallcaps[Lord]] said to #myhl(men)[Joshua], “Get up! Why have you fallen on your face? 
#versenum(11) #myhl(people-groups)[Israel] has sinned; they have transgressed my covenant that I commanded them; they have taken some of the devoted things; they have #underline[stolen] and #underline[lied] and put them among their own #underline[belongings]. 
#versenum(12) Therefore the #myhl(people-groups)[people of Israel] cannot stand before their enemies. They turn their backs before their enemies, because they have become devoted for destruction.#footnote[Joshua 7:12 That is, set apart (devoted) as an offering to the Lord (for destruction)] I will be with you no more, #underline[unless] you destroy the devoted things from among you. 
#versenum(13) Get up! Consecrate the people and say, ‘Consecrate yourselves for tomorrow; for thus says the #myhl(divine)[#smallcaps[Lord], God of Israel], “There are devoted things in your midst, O #myhl(people-groups)[Israel]. You cannot stand before your enemies until you take away the devoted things from among you.” 
#versenum(14) In the morning therefore you shall be brought near by your tribes. And the tribe that the #myhl(divine)[#smallcaps[Lord]] takes by lot shall come near by clans. And the clan that the #myhl(divine)[#smallcaps[Lord]] takes shall come near by #underline[households]. And the household that the #myhl(divine)[#smallcaps[Lord]] takes shall come near man by man. 
#versenum(15) And he who is taken with the devoted things shall be burned with fire, he and all that he has, because he has transgressed the covenant of the #myhl(divine)[#smallcaps[Lord]], and because he has done an outrageous thing in #myhl(places)[Israel].’”


  
#versenum(16) So #myhl(men)[Joshua] rose early in the morning and brought #myhl(people-groups)[Israel] near tribe by tribe, and the tribe of #myhl(other)[Judah] was taken. 
#versenum(17) And he brought near the clans of #myhl(other)[Judah], and the clan of the #myhl(other)[Zerahites] was taken. And he brought near the clan of the #myhl(other)[Zerahites] man by man, and #myhl(men)[Zabdi] was taken. 
#versenum(18) And he brought near his household man by man, and #myhl(men)[Achan] the son of #myhl(men)[Carmi], son of #myhl(men)[Zabdi], son of #myhl(men)[Zerah], of the tribe of #myhl(other)[Judah], was taken. 
#versenum(19) Then #myhl(men)[Joshua] said to #myhl(men)[Achan], “My son, give glory to the #myhl(divine)[#smallcaps[Lord] God of Israel] and give #underline[praise]#footnote[Joshua 7:19 Or #emph[and make confession]] to him. And tell me now what you have done; do not hide it from me.” 
#versenum(20) And #myhl(men)[Achan] answered #myhl(men)[Joshua], “Truly I have sinned against the #myhl(divine)[#smallcaps[Lord] God of Israel], and this is what I did: 
#versenum(21) when I saw among the spoil a beautiful cloak from #underline[#myhl(places)[Shinar]], and #myhl(numbers)[200] shekels of silver, and a bar of gold #underline[weighing] #underline[#myhl(numbers)[50]] shekels,#footnote[Joshua 7:21 A #emph[shekel] was about 2/5 ounce or 11 grams] then I #underline[coveted] them and took them. And see, they are hidden in the earth inside my tent, with the silver underneath.”


  
#versenum(22) So #myhl(men)[Joshua] sent messengers, and they ran to the tent; and behold, it was hidden in his tent with the silver underneath. 
#versenum(23) And they took them out of the tent and brought them to #myhl(men)[Joshua] and to all the #myhl(people-groups)[people of Israel]. And they laid them down before the #myhl(divine)[#smallcaps[Lord]]. 
#versenum(24) And #myhl(men)[Joshua] and all #myhl(people-groups)[Israel] with him took #myhl(men)[Achan] the son of #myhl(men)[Zerah], and the silver and the cloak and the bar of gold, and his sons and daughters and his oxen and donkeys and sheep and his tent and all that he had. And they brought them up to the #myhl(places)[Valley of Achor]. 
#versenum(25) And #myhl(men)[Joshua] said, “Why did you bring trouble on us? The #myhl(divine)[#smallcaps[Lord]] #underline[brings] trouble on you today.” And all #myhl(people-groups)[Israel] stoned him with stones. They burned them with fire and stoned them with stones. 
#versenum(26) And they raised over him a great heap of stones that remains to this day. Then the #myhl(divine)[#smallcaps[Lord]] turned from his #underline[burning] anger. Therefore, to this day the name of that place is called the #myhl(places)[Valley of Achor].#footnote[Joshua 7:26 #emph[Achor] means #emph[trouble]]


  
#chapter-heading[Joshua 8]


#section-heading[The Fall of Ai]


#versenum(1) And the #myhl(divine)[#smallcaps[Lord]] said to #myhl(men)[Joshua], “Do not fear and do not be dismayed. Take all the fighting men with you, and arise, go up to #myhl(places)[Ai]. See, I have given into your hand the king of #myhl(places)[Ai], and his people, his city, and his land. 
#versenum(2) And you shall do to #myhl(places)[Ai] and its king as you did to #myhl(places)[Jericho] and its king. Only its spoil and its livestock you shall take as plunder for yourselves. Lay an ambush against the city, behind it.”


  
#versenum(3) So #myhl(men)[Joshua] and all the fighting men arose to go up to #myhl(places)[Ai]. And #myhl(men)[Joshua] #underline[chose] #underline[#myhl(numbers)[30,000]] mighty men of valor and sent them out by night. 
#versenum(4) And he commanded them, “Behold, you shall lie in ambush against the city, behind it. Do not go very far from the city, but all of you remain ready. 
#versenum(5) And I and all the people who are with me will #underline[approach] the city. And when they come out against us just as before, we shall flee before them. 
#versenum(6) And they will come out after us, until we have drawn them away from the city. For they will say, ‘They are #underline[fleeing] from us, just as before.’ So we will flee before them. 
#versenum(7) Then you shall rise up from the ambush and #underline[seize] the city, for the #myhl(divine)[#smallcaps[Lord] your God] will give it into your hand. 
#versenum(8) And as soon as you have taken the city, you shall set the city on fire. You shall do according to the word of the #myhl(divine)[#smallcaps[Lord]]. See, I have commanded you.” 
#versenum(9) So #myhl(men)[Joshua] sent them out. And they went to the place of ambush and lay between #myhl(places)[Bethel] and #myhl(places)[Ai], to the west of #myhl(places)[Ai], but #myhl(men)[Joshua] spent that night among the people.


  
#versenum(10) #myhl(men)[Joshua] arose early in the morning and mustered the people and went up, he and the elders of #myhl(people-groups)[Israel], before the people to #myhl(places)[Ai]. 
#versenum(11) And all the fighting men who were with him went up and drew near before the city and encamped on the north side of #myhl(places)[Ai], with a #underline[ravine] between them and #myhl(places)[Ai]. 
#versenum(12) He took about #underline[#myhl(numbers)[5,000]] men and set them in ambush between #myhl(places)[Bethel] and #myhl(places)[Ai], to the west of the city. 
#versenum(13) So they #underline[stationed] the forces, the main #underline[encampment] that was north of the city and its rear guard west of the city. But #myhl(men)[Joshua] spent that night in the valley. 
#versenum(14) And as soon as the king of #myhl(places)[Ai] saw this, he and all his people, the men of the city, hurried and went out early to the appointed place#footnote[Joshua 8:14 Hebrew #emph[appointed time]] toward the #myhl(places)[Arabah] to meet #myhl(people-groups)[Israel] in battle. But he did not know that there was an ambush against him behind the city. 
#versenum(15) And #myhl(men)[Joshua] and all #myhl(people-groups)[Israel] #underline[pretended] to be #underline[beaten] before them and fled in the direction of the wilderness. 
#versenum(16) So all the people who were in the city were called together to pursue them, and as they pursued #myhl(men)[Joshua] they were drawn away from the city. 
#versenum(17) Not a man was left in #myhl(places)[Ai] or #myhl(places)[Bethel] who did not go out after #myhl(people-groups)[Israel]. They left the city open and pursued #myhl(people-groups)[Israel].


  
#versenum(18) Then the #myhl(divine)[#smallcaps[Lord]] said to #myhl(men)[Joshua], “#underline[Stretch] out the javelin that is in your hand toward #myhl(places)[Ai], for I will give it into your hand.” And #myhl(men)[Joshua] stretched out the javelin that was in his hand toward the city. 
#versenum(19) And the men in the ambush rose quickly out of their place, and as soon as he had stretched out his hand, they ran and entered the city and captured it. And they hurried to set the city on fire. 
#versenum(20) So when the men of #myhl(places)[Ai] looked back, behold, the smoke of the city went up to heaven, and they had no power to flee this way or that, for the people who fled to the wilderness turned back against the pursuers. 
#versenum(21) And when #myhl(men)[Joshua] and all #myhl(people-groups)[Israel] saw that the ambush had captured the city, and that the smoke of the city went up, then they turned back and struck down the men of #myhl(places)[Ai]. 
#versenum(22) And the others came out from the city against them, so they were in the midst of #myhl(people-groups)[Israel], some on this side, and some on that side. And #myhl(people-groups)[Israel] struck them down, until there was left none that #underline[survived] or escaped. 
#versenum(23) But the king of #myhl(places)[Ai] they took alive, and brought him near to #myhl(men)[Joshua].


  
#versenum(24) When #myhl(people-groups)[Israel] had finished killing all the inhabitants of #myhl(places)[Ai] in the open wilderness where they pursued them, and all of them to the very last had fallen by the edge of the sword, all #myhl(people-groups)[Israel] returned to #myhl(places)[Ai] and struck it down with the edge of the sword. 
#versenum(25) And all who fell that day, both men and women, were #myhl(numbers)[12,000], all the people of #myhl(places)[Ai]. 
#versenum(26) But #myhl(men)[Joshua] did not draw back his hand with which he stretched out the javelin until he had devoted all the inhabitants of #myhl(places)[Ai] to destruction.#footnote[Joshua 8:26 That is, set apart (devoted) as an offering to the Lord (for destruction)] 
#versenum(27) Only the livestock and the spoil of that city #myhl(people-groups)[Israel] took as their plunder, according to the word of the #myhl(divine)[#smallcaps[Lord]] that he commanded #myhl(men)[Joshua]. 
#versenum(28) So #myhl(men)[Joshua] burned #myhl(places)[Ai] and made it forever a heap of #underline[ruins], as it is to this day. 
#versenum(29) And he hanged the king of #myhl(places)[Ai] on a tree until evening. And at #underline[sunset] #myhl(men)[Joshua] commanded, and they took his body down from the tree and threw it at the entrance of the gate of the city and raised over it a great heap of stones, which stands there to this day.


  
#section-heading[Joshua Renews the Covenant]


#versenum(30) At that time #myhl(men)[Joshua] built an altar to the #myhl(divine)[#smallcaps[Lord], the God of Israel], on #myhl(places)[Mount Ebal], 
#versenum(31) just as #myhl(men)[Moses] the servant of the #myhl(divine)[#smallcaps[Lord]] had commanded the #myhl(people-groups)[people of Israel], as it is written in the Book of the Law of #myhl(men)[Moses], “an altar of #underline[uncut] stones, upon which no man has #underline[wielded] an iron #underline[tool].” And they offered on it burnt offerings to the #myhl(divine)[#smallcaps[Lord]] and sacrificed peace offerings. 
#versenum(32) And there, in the presence of the #myhl(people-groups)[people of Israel], he wrote on the stones a copy of the law of #myhl(men)[Moses], which he had written. 
#versenum(33) And all #myhl(people-groups)[Israel], #underline[sojourner] as well as native born, with their elders and officers and their judges, stood on opposite sides of the ark before the Levitical priests who carried the ark of the covenant of the #myhl(divine)[#smallcaps[Lord]], #myhl(numbers)[half] of them in front of #myhl(places)[Mount Gerizim] and #myhl(numbers)[half] of them in front of #myhl(places)[Mount Ebal], just as #myhl(men)[Moses] the servant of the #myhl(divine)[#smallcaps[Lord]] had commanded at the first, to bless the #myhl(people-groups)[people of Israel]. 
#versenum(34) And afterward he read all the words of the law, the blessing and the curse, according to all that is written in the Book of the Law. 
#versenum(35) There was not a word of all that #myhl(men)[Moses] commanded that #myhl(men)[Joshua] did not read before all the assembly of #myhl(people-groups)[Israel], and the women, and the little ones, and the #underline[sojourners] who lived#footnote[Joshua 8:35 Or #emph[traveled]] among them.


  
#chapter-heading[Joshua 9]


#section-heading[The Gibeonite Deception]


#versenum(1) As soon as all the kings who were beyond the #myhl(places)[Jordan] in the hill country and in the lowland all along the coast of the #myhl(places)[Great Sea] toward #myhl(places)[Lebanon], the #myhl(other)[Hittites], the #myhl(other)[Amorites], the #myhl(other)[Canaanites], the #myhl(other)[Perizzites], the #myhl(other)[Hivites], and the #myhl(other)[Jebusites], heard of this, 
#versenum(2) they gathered together as one to fight against #myhl(men)[Joshua] and #myhl(people-groups)[Israel].


  
#versenum(3) But when the inhabitants of #myhl(places)[Gibeon] heard what #myhl(men)[Joshua] had done to #myhl(places)[Jericho] and to #myhl(places)[Ai], 
#versenum(4) they on their part acted with #underline[cunning] and went and made ready provisions and took worn-out #underline[sacks] for their donkeys, and wineskins, worn-out and #underline[torn] and #underline[mended], 
#versenum(5) with worn-out, #underline[patched] sandals on their feet, and worn-out clothes. And all their provisions were dry and crumbly. 
#versenum(6) And they went to #myhl(men)[Joshua] in the camp at #myhl(places)[Gilgal] and said to him and to the #myhl(people-groups)[men of Israel], “We have come from a distant country, so now make a covenant with us.” 
#versenum(7) But the #myhl(people-groups)[men of Israel] said to the #myhl(other)[Hivites], “#underline[Perhaps] you live among us; then how can we make a covenant with you?” 
#versenum(8) They said to #myhl(men)[Joshua], “We are your servants.” And #myhl(men)[Joshua] said to them, “Who are you? And where do you come from?” 
#versenum(9) They said to him, “From a very distant country your servants have come, because of the name of the #myhl(divine)[#smallcaps[Lord] your God]. For we have heard a report of him, and all that he did in #myhl(places)[Egypt], 
#versenum(10) and all that he did to the #myhl(numbers)[two] kings of the #myhl(other)[Amorites] who were beyond the #myhl(places)[Jordan], to #myhl(men)[Sihon] the king of #myhl(places)[Heshbon], and to #myhl(men)[Og] king of #myhl(places)[Bashan], who lived in #myhl(places)[Ashtaroth]. 
#versenum(11) So our elders and all the inhabitants of our country said to us, ‘Take provisions in your hand for the journey and go to meet them and say to them, “We are your servants. Come now, make a covenant with us.”’ 
#versenum(12) Here is our bread. It was still #underline[warm] when we took it from our houses as our food for the journey on the day we set out to come to you, but now, behold, it is dry and crumbly. 
#versenum(13) These wineskins were new when we #underline[filled] them, and behold, they have #underline[burst]. And these garments and sandals of ours are worn out from the very long journey.” 
#versenum(14) So the men took some of their provisions, but did not ask counsel from the #myhl(divine)[#smallcaps[Lord]]. 
#versenum(15) And #myhl(men)[Joshua] made peace with them and made a covenant with them, to let them live, and the leaders of the congregation swore to them.


  
#versenum(16) At the end of #myhl(numbers)[three] days after they had made a covenant with them, they heard that they were their #underline[neighbors] and that they lived among them. 
#versenum(17) And the #myhl(people-groups)[people of Israel] set out and reached their cities on the #myhl(numbers)[third] day. Now their cities were #myhl(places)[Gibeon], #myhl(places)[Chephirah], #myhl(places)[Beeroth], and #myhl(places)[Kiriath-jearim]. 
#versenum(18) But the #myhl(people-groups)[people of Israel] did not attack them, because the leaders of the congregation had sworn to them by the #myhl(divine)[#smallcaps[Lord], the God of Israel]. Then all the congregation #underline[murmured] against the leaders. 
#versenum(19) But all the leaders said to all the congregation, “We have sworn to them by the #myhl(divine)[#smallcaps[Lord], the God of Israel], and now we may not touch them. 
#versenum(20) This we will do to them: let them live, lest wrath be upon us, because of the oath that we swore to them.” 
#versenum(21) And the leaders said to them, “Let them live.” So they became cutters of wood and drawers of water for all the congregation, just as the leaders had said of them.


  
#versenum(22) #myhl(men)[Joshua] summoned them, and he said to them, “Why did you #underline[deceive] us, saying, ‘We are very far from you,’ when you dwell among us? 
#versenum(23) Now therefore you are cursed, and some of you shall never be anything but servants, cutters of wood and drawers of water for the house of my #myhl(divine)[God].” 
#versenum(24) They answered #myhl(men)[Joshua], “Because it was told to your servants for a #underline[certainty] that the #myhl(divine)[#smallcaps[Lord] your God] had commanded his servant #myhl(men)[Moses] to give you all the land and to destroy all the inhabitants of the land from before you—so we feared greatly for our lives because of you and did this thing. 
#versenum(25) And now, behold, we are in your hand. Whatever seems good and right in your sight to do to us, do it.” 
#versenum(26) So he did this to them and delivered them out of the hand of the #myhl(people-groups)[people of Israel], and they did not kill them. 
#versenum(27) But #myhl(men)[Joshua] made them that day cutters of wood and drawers of water for the congregation and for the altar of the #myhl(divine)[#smallcaps[Lord]], to this day, in the place that he should choose.


  
#chapter-heading[Joshua 10]


#section-heading[The Sun Stands Still]


#versenum(1) As soon as #myhl(men)[Adoni-zedek], king of #myhl(places)[Jerusalem], heard how #myhl(men)[Joshua] had captured #myhl(places)[Ai] and had devoted it to destruction,#footnote[Joshua 10:1 That is, set apart (devoted) as an offering to the Lord (for destruction); also verses 28, 35, 37, 39, 40] doing to #myhl(places)[Ai] and its king as he had done to #myhl(places)[Jericho] and its king, and how the inhabitants of #myhl(places)[Gibeon] had made peace with #myhl(people-groups)[Israel] and were among them, 
#versenum(2) he#footnote[Joshua 10:2 One Hebrew manuscript, Vulgate (compare Syriac); most Hebrew manuscripts #emph[they]] feared greatly, because #myhl(places)[Gibeon] was a great city, like one of the #underline[royal] cities, and because it was greater than #myhl(places)[Ai], and all its men were warriors. 
#versenum(3) So #myhl(men)[Adoni-zedek] king of #myhl(places)[Jerusalem] sent to #underline[#myhl(men)[Hoham]] king of #myhl(places)[Hebron], to #underline[#myhl(men)[Piram]] king of #myhl(places)[Jarmuth], to #myhl(men)[Japhia] king of #myhl(places)[Lachish], and to #myhl(men)[Debir] king of #myhl(places)[Eglon], saying, 
#versenum(4) “Come up to me and help me, and let us strike #myhl(places)[Gibeon]. For it has made peace with #myhl(men)[Joshua] and with the #myhl(people-groups)[people of Israel].” 
#versenum(5) Then the #myhl(numbers)[five] kings of the #myhl(other)[Amorites], the king of #myhl(places)[Jerusalem], the king of #myhl(places)[Hebron], the king of #myhl(places)[Jarmuth], the king of #myhl(places)[Lachish], and the king of #myhl(places)[Eglon], gathered their forces and went up with all their #underline[armies] and encamped against #myhl(places)[Gibeon] and made war against it.


  
#versenum(6) And the men of #myhl(places)[Gibeon] sent to #myhl(men)[Joshua] at the camp in #myhl(places)[Gilgal], saying, “Do not #underline[relax] your hand from your servants. Come up to us quickly and save us and help us, for all the kings of the #myhl(other)[Amorites] who dwell in the hill country are gathered against us.” 
#versenum(7) So #myhl(men)[Joshua] went up from #myhl(places)[Gilgal], he and all the people of war with him, and all the mighty men of valor. 
#versenum(8) And the #myhl(divine)[#smallcaps[Lord]] said to #myhl(men)[Joshua], “Do not fear them, for I have given them into your hands. Not a man of them shall stand before you.” 
#versenum(9) So #myhl(men)[Joshua] came upon them suddenly, having marched up all night from #myhl(places)[Gilgal]. 
#versenum(10) And the #myhl(divine)[#smallcaps[Lord]] threw them into a panic before #myhl(people-groups)[Israel], who#footnote[Joshua 10:10 Or #emph[and he]] struck them with a great blow at #myhl(places)[Gibeon] and chased them by the way of the ascent of #myhl(places)[Beth-horon] and struck them as far as #myhl(places)[Azekah] and #myhl(places)[Makkedah]. 
#versenum(11) And as they fled before #myhl(people-groups)[Israel], while they were going down the ascent of #myhl(places)[Beth-horon], the #myhl(divine)[#smallcaps[Lord]] threw down large stones from heaven on them as far as #myhl(places)[Azekah], and they died. There were more who died because of the #underline[hailstones] than the sons of #myhl(people-groups)[Israel] killed with the sword.


  
#versenum(12) At that time #myhl(men)[Joshua] spoke to the #myhl(divine)[#smallcaps[Lord]] in the day when the #myhl(divine)[#smallcaps[Lord]] gave the #myhl(other)[Amorites] over to the sons of #myhl(people-groups)[Israel], and he said in the sight of #myhl(people-groups)[Israel],


    “Sun, stand still at #myhl(places)[Gibeon],\
    #vin and moon, in the #myhl(places)[Valley of Aijalon].”\
    
#versenum(13) And the sun stood still, and the moon stopped,\
    #vin until the nation took vengeance on their enemies.\


      Is this not written in the #myhl(other)[Book of #underline[Jashar]]? The sun stopped in the midst of heaven and did not hurry to set for about a whole day. 
#versenum(14) There has been no day like it before or since, when the #myhl(divine)[#smallcaps[Lord]] #underline[heeded] the voice of a man, for the #myhl(divine)[#smallcaps[Lord]] fought for #myhl(people-groups)[Israel].


  
#versenum(15) So #myhl(men)[Joshua] returned, and all #myhl(people-groups)[Israel] with him, to the camp at #myhl(places)[Gilgal].


  
#section-heading[Five Amorite Kings Executed]


#versenum(16) These #myhl(numbers)[five] kings fled and hid themselves in the cave at #myhl(places)[Makkedah]. 
#versenum(17) And it was told to #myhl(men)[Joshua], “The #myhl(numbers)[five] kings have been found, hidden in the cave at #myhl(places)[Makkedah].” 
#versenum(18) And #myhl(men)[Joshua] said, “#underline[Roll] large stones against the mouth of the cave and set men by it to guard them, 
#versenum(19) but do not stay there yourselves. Pursue your enemies; attack their rear guard. Do not let them enter their cities, for the #myhl(divine)[#smallcaps[Lord] your God] has given them into your hand.” 
#versenum(20) When #myhl(men)[Joshua] and the sons of #myhl(people-groups)[Israel] had finished striking them with a great blow until they were #underline[wiped] out, and when the remnant that remained of them had entered into the fortified cities, 
#versenum(21) then all the people returned #underline[safe] to #myhl(men)[Joshua] in the camp at #myhl(places)[Makkedah]. Not a man moved his tongue against any of the #myhl(people-groups)[people of Israel].


  
#versenum(22) Then #myhl(men)[Joshua] said, “Open the mouth of the cave and bring those #myhl(numbers)[five] kings out to me from the cave.” 
#versenum(23) And they did so, and brought those #myhl(numbers)[five] kings out to him from the cave, the king of #myhl(places)[Jerusalem], the king of #myhl(places)[Hebron], the king of #myhl(places)[Jarmuth], the king of #myhl(places)[Lachish], and the king of #myhl(places)[Eglon]. 
#versenum(24) And when they brought those kings out to #myhl(men)[Joshua], #myhl(men)[Joshua] summoned all the #myhl(people-groups)[men of Israel] and said to the chiefs of the men of war who had gone with him, “Come near; put your feet on the necks of these kings.” Then they came near and put their feet on their necks. 
#versenum(25) And #myhl(men)[Joshua] said to them, “Do not be afraid or dismayed; be strong and courageous. For thus the #myhl(divine)[#smallcaps[Lord]] will do to all your enemies against whom you fight.” 
#versenum(26) And afterward #myhl(men)[Joshua] struck them and put them to death, and he hanged them on #myhl(numbers)[five] trees. And they #underline[hung] on the trees until evening. 
#versenum(27) But at the time of the going down of the sun, #myhl(men)[Joshua] commanded, and they took them down from the trees and threw them into the cave where they had hidden themselves, and they set large stones against the mouth of the cave, which remain to this very day.


  
#versenum(28) As for #myhl(places)[Makkedah], #myhl(men)[Joshua] captured it on that day and struck it, and its king, with the edge of the sword. He devoted to destruction every person in it; he left none remaining. And he did to the king of #myhl(places)[Makkedah] just as he had done to the king of #myhl(places)[Jericho].


  
#section-heading[Conquest of Southern Canaan]


#versenum(29) Then #myhl(men)[Joshua] and all #myhl(people-groups)[Israel] with him passed on from #myhl(places)[Makkedah] to #myhl(places)[Libnah] and fought against #myhl(places)[Libnah]. 
#versenum(30) And the #myhl(divine)[#smallcaps[Lord]] gave it also and its king into the hand of #myhl(people-groups)[Israel]. And he struck it with the edge of the sword, and every person in it; he left none remaining in it. And he did to its king as he had done to the king of #myhl(places)[Jericho].


  
#versenum(31) Then #myhl(men)[Joshua] and all #myhl(people-groups)[Israel] with him passed on from #myhl(places)[Libnah] to #myhl(places)[Lachish] and laid siege to it and fought against it. 
#versenum(32) And the #myhl(divine)[#smallcaps[Lord]] gave #myhl(places)[Lachish] into the hand of #myhl(people-groups)[Israel], and he captured it on the #myhl(numbers)[second] day and struck it with the edge of the sword, and every person in it, as he had done to #myhl(places)[Libnah].


  
#versenum(33) Then #underline[#myhl(men)[Horam]] king of #myhl(places)[Gezer] came up to help #myhl(places)[Lachish]. And #myhl(men)[Joshua] struck him and his people, until he left none remaining.


  
#versenum(34) Then #myhl(men)[Joshua] and all #myhl(people-groups)[Israel] with him passed on from #myhl(places)[Lachish] to #myhl(places)[Eglon]. And they laid siege to it and fought against it. 
#versenum(35) And they captured it on that day, and struck it with the edge of the sword. And he devoted every person in it to destruction that day, as he had done to #myhl(places)[Lachish].


  
#versenum(36) Then #myhl(men)[Joshua] and all #myhl(people-groups)[Israel] with him went up from #myhl(places)[Eglon] to #myhl(places)[Hebron]. And they fought against it 
#versenum(37) and captured it and struck it with the edge of the sword, and its king and its towns, and every person in it. He left none remaining, as he had done to #myhl(places)[Eglon], and devoted it to destruction and every person in it.


  
#versenum(38) Then #myhl(men)[Joshua] and all #myhl(people-groups)[Israel] with him turned back to #myhl(places)[Debir] and fought against it 
#versenum(39) and he captured it with its king and all its towns. And they struck them with the edge of the sword and devoted to destruction every person in it; he left none remaining. Just as he had done to #myhl(places)[Hebron] and to #myhl(places)[Libnah] and its king, so he did to #myhl(places)[Debir] and to its king.


  
#versenum(40) So #myhl(men)[Joshua] struck the whole land, the hill country and the #myhl(places)[Negeb] and the lowland and the slopes, and all their kings. He left none remaining, but devoted to destruction all that breathed, just as the #myhl(divine)[#smallcaps[Lord] God of Israel] commanded. 
#versenum(41) And #myhl(men)[Joshua] struck them from #myhl(places)[Kadesh-barnea] as far as #myhl(places)[Gaza], and all the country of #myhl(places)[Goshen], as far as #myhl(places)[Gibeon]. 
#versenum(42) And #myhl(men)[Joshua] captured all these kings and their land at one time, because the #myhl(divine)[#smallcaps[Lord] God of Israel] fought for #myhl(people-groups)[Israel]. 
#versenum(43) Then #myhl(men)[Joshua] returned, and all #myhl(people-groups)[Israel] with him, to the camp at #myhl(places)[Gilgal].


  
#chapter-heading[Joshua 11]


#section-heading[Conquests in Northern Canaan]


#versenum(1) When #myhl(men)[Jabin], king of #myhl(places)[Hazor], heard of this, he sent to #underline[#myhl(men)[Jobab]] king of #myhl(places)[Madon], and to the king of #myhl(places)[Shimron], and to the king of #myhl(places)[Achshaph], 
#versenum(2) and to the kings who were in the northern hill country, and in the #myhl(places)[Arabah] south of #myhl(places)[Chinneroth], and in the lowland, and in #underline[#myhl(places)[Naphoth-dor]] on the west, 
#versenum(3) to the #myhl(other)[Canaanites] in the east and the west, the #myhl(other)[Amorites], the #myhl(other)[Hittites], the #myhl(other)[Perizzites], and the #myhl(other)[Jebusites] in the hill country, and the #myhl(other)[Hivites] under #myhl(places)[Hermon] in the land of #myhl(places)[Mizpah]. 
#versenum(4) And they came out with all their troops, a great #underline[horde], in number like the sand that is on the seashore, with very many horses and chariots. 
#versenum(5) And all these kings #underline[joined] their forces and came and encamped together at the waters of #myhl(places)[Merom] to fight against #myhl(people-groups)[Israel].


  
#versenum(6) And the #myhl(divine)[#smallcaps[Lord]] said to #myhl(men)[Joshua], “Do not be afraid of them, for tomorrow at this time I will give over all of them, slain, to #myhl(people-groups)[Israel]. You shall #underline[hamstring] their horses and burn their chariots with fire.” 
#versenum(7) So #myhl(men)[Joshua] and all his warriors came suddenly against them by the waters of #myhl(places)[Merom] and fell upon them. 
#versenum(8) And the #myhl(divine)[#smallcaps[Lord]] gave them into the hand of #myhl(people-groups)[Israel], who struck them and chased them as far as Great #myhl(places)[Sidon] and #myhl(places)[Misrephoth-maim], and eastward as far as the #myhl(places)[Valley of Mizpeh]. And they struck them until he left none remaining. 
#versenum(9) And #myhl(men)[Joshua] did to them just as the #myhl(divine)[#smallcaps[Lord]] said to him: he #underline[hamstrung] their horses and burned their chariots with fire.


  
#versenum(10) And #myhl(men)[Joshua] turned back at that time and captured #myhl(places)[Hazor] and struck its king with the sword, for #myhl(places)[Hazor] formerly was the head of all those #underline[kingdoms]. 
#versenum(11) And they struck with the sword all who were in it, devoting them to destruction;#footnote[Joshua 11:11 That is, setting apart (devoting) as an offering to the Lord (for destruction); also verses 12, 20, 21] there was none left that breathed. And he burned #myhl(places)[Hazor] with fire. 
#versenum(12) And all the cities of those kings, and all their kings, #myhl(men)[Joshua] captured, and struck them with the edge of the sword, devoting them to destruction, just as #myhl(men)[Moses] the servant of the #myhl(divine)[#smallcaps[Lord]] had commanded. 
#versenum(13) But none of the cities that stood on #underline[mounds] did #myhl(people-groups)[Israel] burn, except #myhl(places)[Hazor] alone; that #myhl(men)[Joshua] burned. 
#versenum(14) And all the spoil of these cities and the livestock, the #myhl(people-groups)[people of Israel] took for their plunder. But every person they struck with the edge of the sword until they had destroyed them, and they did not leave any who breathed. 
#versenum(15) Just as the #myhl(divine)[#smallcaps[Lord]] had commanded #myhl(men)[Moses] his servant, so #myhl(men)[Moses] commanded #myhl(men)[Joshua], and so #myhl(men)[Joshua] did. He left nothing #underline[undone] of all that the #myhl(divine)[#smallcaps[Lord]] had commanded #myhl(men)[Moses].


  
#versenum(16) So #myhl(men)[Joshua] took all that land, the hill country and all the #myhl(places)[Negeb] and all the land of #myhl(places)[Goshen] and the lowland and the #myhl(places)[Arabah] and #myhl(places)[the hill country of Israel] and its lowland 
#versenum(17) from #myhl(places)[Mount Halak], which rises toward #myhl(places)[Seir], as far as #myhl(places)[Baal-gad] in the #myhl(places)[Valley of Lebanon] below #myhl(places)[Mount Hermon]. And he captured all their kings and struck them and put them to death. 
#versenum(18) #myhl(men)[Joshua] made war a long time with all those kings. 
#versenum(19) There was not a city that made peace with the #myhl(people-groups)[people of Israel] except the #myhl(other)[Hivites], the inhabitants of #myhl(places)[Gibeon]. They took them all in battle. 
#versenum(20) For it was the #myhl(divine)[#smallcaps[Lord]]’s doing to #underline[harden] their hearts that they should come against #myhl(people-groups)[Israel] in battle, in order that they should be devoted to destruction and should #underline[receive] no #underline[mercy] but be destroyed, just as the #myhl(divine)[#smallcaps[Lord]] commanded #myhl(men)[Moses].


  
#versenum(21) And #myhl(men)[Joshua] came at that time and cut off the #myhl(other)[Anakim] from the hill country, from #myhl(places)[Hebron], from #myhl(places)[Debir], from #myhl(places)[Anab], and from all #myhl(places)[the hill country of Judah], and from all #myhl(places)[the hill country of Israel]. #myhl(men)[Joshua] devoted them to destruction with their cities. 
#versenum(22) There was none of the #myhl(other)[Anakim] left in the land of the #myhl(people-groups)[people of Israel]. Only in #myhl(places)[Gaza], in Gath, and in #myhl(places)[Ashdod] did some remain. 
#versenum(23) So #myhl(men)[Joshua] took the whole land, according to all that the #myhl(divine)[#smallcaps[Lord]] had spoken to #myhl(men)[Moses]. And #myhl(men)[Joshua] gave it for an inheritance to #myhl(people-groups)[Israel] according to their tribal allotments. And the land had rest from war.


  
#chapter-heading[Joshua 12]


#section-heading[Kings Defeated by Moses]


#versenum(1) Now these are the kings of the land whom the #myhl(people-groups)[people of Israel] defeated and took possession of their land beyond the #myhl(places)[Jordan] toward the sunrise, from the #myhl(places)[Valley of the Arnon] to #myhl(places)[Mount Hermon], with all the #myhl(places)[Arabah] eastward: 
#versenum(2) #myhl(men)[Sihon] king of the #myhl(other)[Amorites] who lived at #myhl(places)[Heshbon] and ruled from #myhl(places)[Aroer], which is on the edge of the #myhl(places)[Valley of the Arnon], and from the middle of the valley as far as the river #myhl(places)[Jabbok], the boundary of the #myhl(other)[Ammonites], that is, #myhl(numbers)[half] of #myhl(places)[Gilead], 
#versenum(3) and the #myhl(places)[Arabah] to the #myhl(places)[Sea of Chinneroth] eastward, and in the direction of #myhl(places)[Beth-jeshimoth], to the #myhl(places)[Sea of the Arabah], the #myhl(places)[Salt Sea], southward to the foot of the slopes of #myhl(places)[Pisgah]; 
#versenum(4) and #myhl(men)[Og]#footnote[Joshua 12:4 Septuagint; Hebrew #emph[the boundary of Og]] king of #myhl(places)[Bashan], one of the remnant of the #myhl(other)[Rephaim], who lived at #myhl(places)[Ashtaroth] and at #myhl(places)[Edrei] 
#versenum(5) and ruled over #myhl(places)[Mount Hermon] and #myhl(places)[Salecah] and all #myhl(places)[Bashan] to the boundary of the #myhl(other)[Geshurites] and the #myhl(other)[Maacathites], and over #myhl(numbers)[half] of #myhl(places)[Gilead] to the boundary of #myhl(men)[Sihon] king of #myhl(places)[Heshbon]. 
#versenum(6) #myhl(men)[Moses], the servant of the #myhl(divine)[#smallcaps[Lord]], and the #myhl(people-groups)[people of Israel] defeated them. And #myhl(men)[Moses] the servant of the #myhl(divine)[#smallcaps[Lord]] gave their land for a possession to the #myhl(other)[Reubenites] and the #myhl(other)[Gadites] and the #myhl(numbers)[half]-tribe of #myhl(other)[Manasseh].


  
#section-heading[Kings Defeated by Joshua]


#versenum(7) And these are the kings of the land whom #myhl(men)[Joshua] and the #myhl(people-groups)[people of Israel] defeated on the west side of the #myhl(places)[Jordan], from #myhl(places)[Baal-gad] in the #myhl(places)[Valley of Lebanon] to #myhl(places)[Mount Halak], that rises toward #myhl(places)[Seir] (and #myhl(men)[Joshua] gave their land to the tribes of #myhl(people-groups)[Israel] as a possession according to their allotments, 
#versenum(8) in the hill country, in the lowland, in the #myhl(places)[Arabah], in the slopes, in the wilderness, and in the #myhl(places)[Negeb], the land of the #myhl(other)[Hittites], the #myhl(other)[Amorites], the #myhl(other)[Canaanites], the #myhl(other)[Perizzites], the #myhl(other)[Hivites], and the #myhl(other)[Jebusites]): 
#versenum(9) the king of #myhl(places)[Jericho], #myhl(numbers)[one]; the king of #myhl(places)[Ai], which is beside #myhl(places)[Bethel], #myhl(numbers)[one]; 
#versenum(10) the king of #myhl(places)[Jerusalem], #myhl(numbers)[one]; the king of #myhl(places)[Hebron], #myhl(numbers)[one]; 
#versenum(11) the king of #myhl(places)[Jarmuth], #myhl(numbers)[one]; the king of #myhl(places)[Lachish], #myhl(numbers)[one]; 
#versenum(12) the king of #myhl(places)[Eglon], #myhl(numbers)[one]; the king of #myhl(places)[Gezer], #myhl(numbers)[one]; 
#versenum(13) the king of #myhl(places)[Debir], #myhl(numbers)[one]; the king of #underline[#myhl(places)[Geder]], #myhl(numbers)[one]; 
#versenum(14) the king of #myhl(places)[Hormah], #myhl(numbers)[one]; the king of #myhl(places)[Arad], #myhl(numbers)[one]; 
#versenum(15) the king of #myhl(places)[Libnah], #myhl(numbers)[one]; the king of #myhl(places)[Adullam], #myhl(numbers)[one]; 
#versenum(16) the king of #myhl(places)[Makkedah], #myhl(numbers)[one]; the king of #myhl(places)[Bethel], #myhl(numbers)[one]; 
#versenum(17) the king of #myhl(places)[Tappuah], #myhl(numbers)[one]; the king of #myhl(places)[Hepher], #myhl(numbers)[one]; 
#versenum(18) the king of #myhl(places)[Aphek], #myhl(numbers)[one]; the king of #underline[#myhl(places)[Lasharon]], #myhl(numbers)[one]; 
#versenum(19) the king of #myhl(places)[Madon], #myhl(numbers)[one]; the king of #myhl(places)[Hazor], #myhl(numbers)[one]; 
#versenum(20) the king of #underline[#myhl(places)[Shimron-meron]], #myhl(numbers)[one]; the king of #myhl(places)[Achshaph], #myhl(numbers)[one]; 
#versenum(21) the king of #myhl(places)[Taanach], #myhl(numbers)[one]; the king of #myhl(places)[Megiddo], #myhl(numbers)[one]; 
#versenum(22) the king of #myhl(places)[Kedesh], #myhl(numbers)[one]; the king of #myhl(places)[Jokneam] in #myhl(places)[Carmel], #myhl(numbers)[one]; 
#versenum(23) the king of #myhl(places)[Dor] in #underline[#myhl(places)[Naphath-dor]], #myhl(numbers)[one]; the king of #underline[#myhl(places)[Goiim]] in #myhl(places)[Galilee],#footnote[Joshua 12:23 Septuagint; Hebrew #emph[Gilgal]] #myhl(numbers)[one]; 
#versenum(24) the king of #myhl(places)[Tirzah], #myhl(numbers)[one]: in all, #underline[#myhl(numbers)[thirty-one]] kings.


  
#chapter-heading[Joshua 13]


#section-heading[Land Still to Be Conquered]


#versenum(1) Now #myhl(men)[Joshua] was old and advanced in years, and the #myhl(divine)[#smallcaps[Lord]] said to him, “You are old and advanced in years, and there remains yet very much land to possess. 
#versenum(2) This is the land that yet remains: all the #underline[regions] of the #myhl(other)[Philistines], and all those of the #myhl(other)[Geshurites] 
#versenum(3) (from the #underline[#myhl(places)[Shihor]], which is east of #myhl(places)[Egypt], northward to the boundary of #myhl(places)[Ekron], it is counted as #underline[#myhl(other)[Canaanite]]; there are #myhl(numbers)[five] rulers of the #myhl(other)[Philistines], those of #myhl(places)[Gaza], #myhl(places)[Ashdod], #myhl(places)[Ashkelon], Gath, and #myhl(places)[Ekron]), and those of the #myhl(other)[Avvim], 
#versenum(4) in the south, all the land of the #myhl(other)[Canaanites], and #underline[#myhl(places)[Mearah]] that belongs to the #myhl(other)[Sidonians], to #myhl(places)[Aphek], to the boundary of the #myhl(other)[Amorites], 
#versenum(5) and the land of the #underline[#myhl(other)[Gebalites]], and all #myhl(places)[Lebanon], toward the sunrise, from #myhl(places)[Baal-gad] below #myhl(places)[Mount Hermon] to #myhl(places)[Lebo-hamath], 
#versenum(6) all the inhabitants of the hill country from #myhl(places)[Lebanon] to #myhl(places)[Misrephoth-maim], even all the #myhl(other)[Sidonians]. I myself will drive them out from before the #myhl(people-groups)[people of Israel]. Only #underline[allot] the land to #myhl(people-groups)[Israel] for an inheritance, as I have commanded you. 
#versenum(7) Now therefore divide this land for an inheritance to the #myhl(numbers)[nine] tribes and #myhl(numbers)[half] the tribe of #myhl(other)[Manasseh].”


  
#section-heading[The Inheritance East of the Jordan]


#versenum(8) With the other #myhl(numbers)[half] of the tribe of #myhl(other)[Manasseh]#footnote[Joshua 13:8 Hebrew #emph[With it]] the #myhl(other)[Reubenites] and the #myhl(other)[Gadites] received their inheritance, which #myhl(men)[Moses] gave them, beyond the #myhl(places)[Jordan] eastward, as #myhl(men)[Moses] the servant of the #myhl(divine)[#smallcaps[Lord]] gave them: 
#versenum(9) from #myhl(places)[Aroer], which is on the edge of the #myhl(places)[Valley of the Arnon], and the city that is in the middle of the valley, and all the tableland of #myhl(places)[Medeba] as far as #myhl(places)[Dibon]; 
#versenum(10) and all the cities of #myhl(men)[Sihon] king of the #myhl(other)[Amorites], who reigned in #myhl(places)[Heshbon], as far as the boundary of the #myhl(other)[Ammonites]; 
#versenum(11) and #myhl(places)[Gilead], and the region of the #myhl(other)[Geshurites] and #myhl(other)[Maacathites], and all #myhl(places)[Mount Hermon], and all #myhl(places)[Bashan] to #myhl(places)[Salecah]; 
#versenum(12) all the kingdom of #myhl(men)[Og] in #myhl(places)[Bashan], who reigned in #myhl(places)[Ashtaroth] and in #myhl(places)[Edrei] (he alone was left of the remnant of the #myhl(other)[Rephaim]); these #myhl(men)[Moses] had struck and driven out. 
#versenum(13) Yet the #myhl(people-groups)[people of Israel] did not drive out the #myhl(other)[Geshurites] or the #myhl(other)[Maacathites], but #underline[#myhl(places)[Geshur]] and #underline[#myhl(places)[Maacath]] dwell in the midst of #myhl(people-groups)[Israel] to this day.


  
#versenum(14) To the tribe of #myhl(other)[Levi] alone #myhl(men)[Moses] gave no inheritance. The offerings by fire to the #myhl(divine)[#smallcaps[Lord] God of Israel] are their inheritance, as he said to him.


  
#versenum(15) And #myhl(men)[Moses] gave an inheritance to the tribe of the people of #myhl(other)[Reuben] according to their clans. 
#versenum(16) So their territory was from #myhl(places)[Aroer], which is on the edge of the #myhl(places)[Valley of the Arnon], and the city that is in the middle of the valley, and all the tableland by #myhl(places)[Medeba]; 
#versenum(17) with #myhl(places)[Heshbon], and all its cities that are in the tableland; #myhl(places)[Dibon], and #underline[#myhl(places)[Bamoth-baal]], and #underline[#myhl(places)[Beth-baal-meon]], 
#versenum(18) and #myhl(places)[Jahaz], and #myhl(places)[Kedemoth], and #myhl(places)[Mephaath], 
#versenum(19) and #underline[#myhl(places)[Kiriathaim]], and #underline[#myhl(places)[Sibmah]], and #underline[#myhl(places)[Zereth-shahar]] on the hill of the valley, 
#versenum(20) and #underline[#myhl(places)[Beth-peor]], and the slopes of #myhl(places)[Pisgah], and #myhl(places)[Beth-jeshimoth], 
#versenum(21) that is, all the cities of the tableland, and all the kingdom of #myhl(men)[Sihon] king of the #myhl(other)[Amorites], who reigned in #myhl(places)[Heshbon], whom #myhl(men)[Moses] defeated with the leaders of #myhl(places)[Midian], #underline[#myhl(men)[Evi]] and #myhl(men)[Rekem] and #underline[#myhl(men)[Zur]] and #underline[#myhl(men)[Hur]] and #underline[#myhl(men)[Reba]], the princes of #myhl(men)[Sihon], who lived in the land. 
#versenum(22) #myhl(men)[Balaam] also, the son of #myhl(men)[Beor], the one who #underline[practiced] #underline[divination], was killed with the sword by the #myhl(people-groups)[people of Israel] among the rest of their slain. 
#versenum(23) And the border of the people of #myhl(other)[Reuben] was the #myhl(places)[Jordan] as a boundary. This was the inheritance of the people of #myhl(other)[Reuben], according to their clans with their cities and villages.


  
#versenum(24) #myhl(men)[Moses] gave an inheritance also to the tribe of #myhl(other)[Gad], to the people of #myhl(other)[Gad], according to their clans. 
#versenum(25) Their territory was #myhl(places)[Jazer], and all the cities of #myhl(places)[Gilead], and #myhl(numbers)[half] the land of the #myhl(other)[Ammonites], to #myhl(places)[Aroer], which is east of #myhl(places)[Rabbah], 
#versenum(26) and from #myhl(places)[Heshbon] to #underline[#myhl(places)[Ramath-mizpeh]] and #underline[#myhl(places)[Betonim]], and from #myhl(places)[Mahanaim] to the territory of #myhl(places)[Debir],#footnote[Joshua 13:26 Septuagint, Syriac, Vulgate; Hebrew #emph[Lidebir]] 
#versenum(27) and in the valley #underline[#myhl(places)[Beth-haram]], #underline[#myhl(places)[Beth-nimrah]], #myhl(places)[Succoth], and #myhl(places)[Zaphon], the rest of the kingdom of #myhl(men)[Sihon] king of #myhl(places)[Heshbon], having the #myhl(places)[Jordan] as a boundary, to the lower end of the #myhl(places)[Sea of Chinnereth], eastward beyond the #myhl(places)[Jordan]. 
#versenum(28) This is the inheritance of the people of #myhl(other)[Gad] according to their clans, with their cities and villages.


  
#versenum(29) And #myhl(men)[Moses] gave an inheritance to the #myhl(numbers)[half]-tribe of #myhl(other)[Manasseh]. It was allotted to the #myhl(numbers)[half]-tribe of the people of #myhl(other)[Manasseh] according to their clans. 
#versenum(30) Their region #underline[extended] from #myhl(places)[Mahanaim], through all #myhl(places)[Bashan], the whole kingdom of #myhl(men)[Og] king of #myhl(places)[Bashan], and all the towns of #myhl(men)[Jair], which are in #myhl(places)[Bashan], #underline[#myhl(numbers)[sixty]] cities, 
#versenum(31) and #myhl(numbers)[half] #myhl(places)[Gilead], and #myhl(places)[Ashtaroth], and #myhl(places)[Edrei], the cities of the kingdom of #myhl(men)[Og] in #myhl(places)[Bashan]. These were allotted to the people of #myhl(men)[Machir] the son of #myhl(men)[Manasseh] for the #myhl(numbers)[half] of the people of #myhl(men)[Machir] according to their clans.


  
#versenum(32) These are the inheritances that #myhl(men)[Moses] distributed in the plains of #myhl(places)[Moab], beyond the #myhl(places)[Jordan] east of #myhl(places)[Jericho]. 
#versenum(33) But to the tribe of #myhl(other)[Levi] #myhl(men)[Moses] gave no inheritance; the #myhl(divine)[#smallcaps[Lord] God of Israel] is their inheritance, just as he said to them.


  
#chapter-heading[Joshua 14]


#section-heading[The Inheritance West of the Jordan]


#versenum(1) These are the inheritances that the #myhl(people-groups)[people of Israel] received in the land of #myhl(places)[Canaan], which #myhl(men)[Eleazar] the priest and #myhl(men)[Joshua] the son of #myhl(men)[Nun] and the heads of the fathers’ houses of the tribes of the #myhl(people-groups)[people of Israel] gave them to inherit. 
#versenum(2) Their inheritance was by lot, just as the #myhl(divine)[#smallcaps[Lord]] had commanded by the hand of #myhl(men)[Moses] for the #myhl(numbers)[nine and one-half] tribes. 
#versenum(3) For #myhl(men)[Moses] had given an inheritance to the #myhl(numbers)[two and one-half] tribes beyond the #myhl(places)[Jordan], but to the #myhl(other)[Levites] he gave no inheritance among them. 
#versenum(4) For the people of #myhl(men)[Joseph] were #myhl(numbers)[two] tribes, #myhl(other)[Manasseh] and #myhl(other)[Ephraim]. And no portion was given to the #myhl(other)[Levites] in the land, but only cities to dwell in, with their pasturelands for their livestock and their #underline[substance]. 
#versenum(5) The #myhl(people-groups)[people of Israel] did as the #myhl(divine)[#smallcaps[Lord]] commanded #myhl(men)[Moses]; they allotted the land.


  
#section-heading[Caleb’s Request and Inheritance]


#versenum(6) Then the people of #myhl(other)[Judah] came to #myhl(men)[Joshua] at #myhl(places)[Gilgal]. And #myhl(men)[Caleb] the son of #myhl(men)[Jephunneh] the #myhl(other)[Kenizzite] said to him, “You know what the #myhl(divine)[#smallcaps[Lord]] said to #myhl(men)[Moses] the man of #myhl(divine)[God] in #myhl(places)[Kadesh-barnea] concerning you and me. 
#versenum(7) I was #myhl(numbers)[forty] years old when #myhl(men)[Moses] the servant of the #myhl(divine)[#smallcaps[Lord]] sent me from #myhl(places)[Kadesh-barnea] to spy out the land, and I brought him word again as it was in my heart. 
#versenum(8) But my brothers who went up with me made the heart of the people melt; yet I wholly followed the #myhl(divine)[#smallcaps[Lord]] my #myhl(divine)[God]. 
#versenum(9) And #myhl(men)[Moses] swore on that day, saying, ‘Surely the land on which your foot has #underline[trodden] shall be an inheritance for you and your children forever, because you have wholly followed the #myhl(divine)[#smallcaps[Lord]] my #myhl(divine)[God].’ 
#versenum(10) And now, behold, the #myhl(divine)[#smallcaps[Lord]] has kept me alive, just as he said, these #underline[#myhl(numbers)[forty-five]] years since the time that the #myhl(divine)[#smallcaps[Lord]] spoke this word to #myhl(men)[Moses], while #myhl(people-groups)[Israel] walked in the wilderness. And now, behold, I am this day #underline[#myhl(numbers)[eighty-five]] years old. 
#versenum(11) I am still as strong today as I was in the day that #myhl(men)[Moses] sent me; my strength now is as my strength was then, for war and for going and coming. 
#versenum(12) So now give me this hill country of which the #myhl(divine)[#smallcaps[Lord]] spoke on that day, for you heard on that day how the #myhl(other)[Anakim] were there, with great fortified cities. It may be that the #myhl(divine)[#smallcaps[Lord]] will be with me, and I shall drive them out just as the #myhl(divine)[#smallcaps[Lord]] said.”


  
#versenum(13) Then #myhl(men)[Joshua] blessed him, and he gave #myhl(places)[Hebron] to #myhl(men)[Caleb] the son of #myhl(men)[Jephunneh] for an inheritance. 
#versenum(14) Therefore #myhl(places)[Hebron] became the inheritance of #myhl(men)[Caleb] the son of #myhl(men)[Jephunneh] the #myhl(other)[Kenizzite] to this day, because he wholly followed the #myhl(divine)[#smallcaps[Lord], the God of Israel]. 
#versenum(15) Now the name of #myhl(places)[Hebron] formerly was #myhl(places)[Kiriath-arba].#footnote[Joshua 14:15 #emph[Kiriath-arba] means #emph[the city of Arba]] (#myhl(men)[Arba]#footnote[Joshua 14:15 Hebrew #emph[He]] was the #underline[greatest] man among the #myhl(other)[Anakim].) And the land had rest from war.


  
#chapter-heading[Joshua 15]


#section-heading[The Allotment for Judah]


#versenum(1) The allotment for the tribe of the people of #myhl(other)[Judah] according to their clans reached southward to the boundary of #myhl(places)[Edom], to the wilderness of #myhl(places)[Zin] at the farthest south. 
#versenum(2) And their south boundary ran from the end of the #myhl(places)[Salt Sea], from the bay that faces southward. 
#versenum(3) It goes out southward of the ascent of #myhl(places)[Akrabbim], passes along to #myhl(places)[Zin], and goes up south of #myhl(places)[Kadesh-barnea], along by #myhl(places)[Hezron], up to #underline[#myhl(places)[Addar]], turns about to #underline[#myhl(places)[Karka]], 
#versenum(4) passes along to #underline[#myhl(places)[Azmon]], goes out by the #myhl(places)[Brook of Egypt], and comes to its end at the sea. This shall be your south boundary. 
#versenum(5) And the east boundary is the #myhl(places)[Salt Sea], to the mouth of the #myhl(places)[Jordan]. And the boundary on the north side #underline[runs] from the bay of the sea at the mouth of the #myhl(places)[Jordan]. 
#versenum(6) And the boundary goes up to #myhl(places)[Beth-hoglah] and passes along north of #myhl(places)[Beth-arabah]. And the boundary goes up to the stone of #myhl(men)[Bohan] the son of #myhl(men)[Reuben]. 
#versenum(7) And the boundary goes up to #myhl(places)[Debir] from the #myhl(places)[Valley of Achor], and so northward, turning toward #myhl(places)[Gilgal], which is opposite the ascent of #myhl(places)[Adummim], which is on the south side of the valley. And the boundary passes along to the waters of #myhl(places)[En-shemesh] and ends at #myhl(places)[En-rogel]. 
#versenum(8) Then the boundary goes up by the #myhl(places)[Valley of the Son of Hinnom] at the southern shoulder of the #underline[#myhl(other)[Jebusite]] (that is, #myhl(places)[Jerusalem]). And the boundary goes up to the top of the mountain that lies over against the #myhl(places)[Valley of Hinnom], on the west, at the northern end of the #myhl(places)[Valley of Rephaim]. 
#versenum(9) Then the boundary #underline[extends] from the top of the mountain to the spring of the waters of #myhl(places)[Nephtoah], and from there to the cities of #myhl(places)[Mount Ephron]. Then the boundary bends around to #myhl(places)[Baalah] (that is, #myhl(places)[Kiriath-jearim]). 
#versenum(10) And the boundary #underline[circles] west of #myhl(places)[Baalah] to #myhl(places)[Mount Seir], passes along to the northern shoulder of #myhl(places)[Mount #underline[Jearim]] (that is, #underline[#myhl(places)[Chesalon]]), and goes down to #myhl(places)[Beth-shemesh] and passes along by #myhl(places)[Timnah]. 
#versenum(11) The boundary goes out to the shoulder of the hill north of #myhl(places)[Ekron], then the boundary bends around to #underline[#myhl(places)[Shikkeron]] and passes along to #myhl(places)[Mount Baalah] and goes out to #myhl(places)[Jabneel]. Then the boundary comes to an end at the sea. 
#versenum(12) And the west boundary was the #myhl(places)[Great Sea] with its coastline. This is the boundary around the people of #myhl(other)[Judah] according to their clans.


  
#versenum(13) According to the commandment of the #myhl(divine)[#smallcaps[Lord]] to #myhl(men)[Joshua], he gave to #myhl(men)[Caleb] the son of #myhl(men)[Jephunneh] a portion among the people of #myhl(other)[Judah], #myhl(places)[Kiriath-arba], that is, #myhl(places)[Hebron] (#myhl(men)[Arba] was the father of #myhl(men)[Anak]). 
#versenum(14) And #myhl(men)[Caleb] drove out from there the #myhl(numbers)[three] sons of #myhl(men)[Anak], #myhl(men)[Sheshai] and #myhl(men)[Ahiman] and #myhl(men)[Talmai], the descendants of #myhl(men)[Anak]. 
#versenum(15) And he went up from there against the inhabitants of #myhl(places)[Debir]. Now the name of #myhl(places)[Debir] formerly was #myhl(places)[Kiriath-sepher]. 
#versenum(16) And #myhl(men)[Caleb] said, “He who attacks #myhl(places)[Kiriath-sepher] and captures it, I will give him #myhl(women)[Achsah] my daughter as wife.” 
#versenum(17) And #myhl(men)[Othniel] the son of #myhl(men)[Kenaz], the brother of #myhl(men)[Caleb], captured it. And he gave him #myhl(women)[Achsah] his daughter as wife. 
#versenum(18) When she came to him, she urged him to ask her father for a field. And she dismounted from her donkey, and #myhl(men)[Caleb] said to her, “What do you want?” 
#versenum(19) She said to him, “Give me a blessing. Since you have given me the land of the #myhl(places)[Negeb], give me also springs of water.” And he gave her the upper springs and the lower springs.


  
#versenum(20) This is the inheritance of the tribe of the people of #myhl(other)[Judah] according to their clans. 
#versenum(21) The cities belonging to the tribe of the people of #myhl(other)[Judah] in the #underline[extreme] south, toward the boundary of #myhl(places)[Edom], were #underline[#myhl(places)[Kabzeel]], #underline[#myhl(places)[Eder]], #underline[#myhl(places)[Jagur]], 
#versenum(22) #underline[#myhl(places)[Kinah]], #underline[#myhl(places)[Dimonah]], #underline[#myhl(places)[Adadah]], 
#versenum(23) #myhl(places)[Kedesh], #myhl(places)[Hazor], #underline[#myhl(places)[Ithnan]], 
#versenum(24) #myhl(places)[Ziph], #underline[#myhl(places)[Telem]], #underline[#myhl(places)[Bealoth]], 
#versenum(25) #underline[#myhl(places)[Hazor-hadattah]], #underline[#myhl(places)[Kerioth-hezron]] (that is, #myhl(places)[Hazor]), 
#versenum(26) #underline[#myhl(places)[Amam]], #underline[#myhl(places)[Shema]], #myhl(places)[Moladah], 
#versenum(27) #underline[#myhl(places)[Hazar-gaddah]], #underline[#myhl(places)[Heshmon]], #underline[#myhl(places)[Beth-pelet]], 
#versenum(28) #myhl(places)[Hazar-shual], #myhl(places)[Beersheba], #underline[#myhl(places)[Biziothiah]], 
#versenum(29) #myhl(places)[Baalah], #underline[#myhl(places)[Iim]], #myhl(places)[Ezem], 
#versenum(30) #myhl(places)[Eltolad], #underline[#myhl(places)[Chesil]], #myhl(places)[Hormah], 
#versenum(31) #myhl(places)[Ziklag], #underline[#myhl(places)[Madmannah]], #underline[#myhl(places)[Sansannah]], 
#versenum(32) #underline[#myhl(places)[Lebaoth]], #underline[#myhl(places)[Shilhim]], #myhl(places)[Ain], and #myhl(places)[Rimmon]: in all, #underline[#myhl(numbers)[twenty-nine]] cities with their villages.


  
#versenum(33) And in the lowland, #myhl(places)[Eshtaol], #myhl(places)[Zorah], #myhl(places)[Ashnah], 
#versenum(34) #myhl(places)[Zanoah], #myhl(places)[En-gannim], #myhl(places)[Tappuah], #underline[#myhl(places)[Enam]], 
#versenum(35) #myhl(places)[Jarmuth], #myhl(places)[Adullam], #myhl(places)[Socoh], #myhl(places)[Azekah], 
#versenum(36) #underline[#myhl(places)[Shaaraim]], #underline[#myhl(places)[Adithaim]], #underline[#myhl(places)[Gederah]], #underline[#myhl(places)[Gederothaim]]: #myhl(numbers)[fourteen] cities with their villages.


  
#versenum(37) #underline[#myhl(places)[Zenan]], #underline[#myhl(places)[Hadashah]], #underline[#myhl(places)[Migdal-gad]], 
#versenum(38) #underline[#myhl(places)[Dilean]], #myhl(places)[Mizpeh], #underline[#myhl(places)[Joktheel]], 
#versenum(39) #myhl(places)[Lachish], #underline[#myhl(places)[Bozkath]], #myhl(places)[Eglon], 
#versenum(40) #underline[#myhl(places)[Cabbon]], #underline[#myhl(places)[Lahmam]], #underline[#myhl(places)[Chitlish]], 
#versenum(41) #underline[#myhl(places)[Gederoth]], #myhl(places)[Beth-dagon], #underline[#myhl(places)[Naamah]], and #myhl(places)[Makkedah]: #myhl(numbers)[sixteen] cities with their villages.


  
#versenum(42) #myhl(places)[Libnah], #myhl(places)[Ether], #myhl(places)[Ashan], 
#versenum(43) #underline[#myhl(places)[Iphtah]], #myhl(places)[Ashnah], #underline[#myhl(places)[Nezib]], 
#versenum(44) #underline[#myhl(places)[Keilah]], #myhl(places)[Achzib], and #underline[#myhl(places)[Mareshah]]: #myhl(numbers)[nine] cities with their villages.


  
#versenum(45) #myhl(places)[Ekron], with its towns and its villages; 
#versenum(46) from #myhl(places)[Ekron] to the sea, all that were by the side of #myhl(places)[Ashdod], with their villages.


  
#versenum(47) #myhl(places)[Ashdod], its towns and its villages; #myhl(places)[Gaza], its towns and its villages; to the #myhl(places)[Brook of Egypt], and the #myhl(places)[Great Sea] with its coastline.


  
#versenum(48) And in the hill country, #myhl(places)[Shamir], #myhl(places)[Jattir], #myhl(places)[Socoh], 
#versenum(49) #underline[#myhl(places)[Dannah]], #underline[#myhl(places)[Kiriath-sannah]] (that is, #myhl(places)[Debir]), 
#versenum(50) #myhl(places)[Anab], #underline[#myhl(places)[Eshtemoh]], #underline[#myhl(places)[Anim]], 
#versenum(51) #myhl(places)[Goshen], #myhl(places)[Holon], and #underline[#myhl(places)[Giloh]]: #underline[#myhl(numbers)[eleven]] cities with their villages.


  
#versenum(52) #underline[#myhl(places)[Arab]], #underline[#myhl(places)[Dumah]], #underline[#myhl(places)[Eshan]], 
#versenum(53) #underline[#myhl(places)[Janim]], #underline[#myhl(places)[Beth-tappuah]], #underline[#myhl(places)[Aphekah]], 
#versenum(54) #underline[#myhl(places)[Humtah]], #myhl(places)[Kiriath-arba] (that is, #myhl(places)[Hebron]), and #underline[#myhl(places)[Zior]]: #myhl(numbers)[nine] cities with their villages.


  
#versenum(55) #underline[#myhl(places)[Maon]], #myhl(places)[Carmel], #myhl(places)[Ziph], #myhl(places)[Juttah], 
#versenum(56) #myhl(places)[Jezreel], #underline[#myhl(places)[Jokdeam]], #myhl(places)[Zanoah], 
#versenum(57) #underline[#myhl(places)[Kain]], #myhl(places)[Gibeah], and #myhl(places)[Timnah]: #myhl(numbers)[ten] cities with their villages.


  
#versenum(58) #underline[#myhl(places)[Halhul]], #underline[#myhl(places)[Beth-zur]], #underline[#myhl(places)[Gedor]], 
#versenum(59) #underline[#myhl(places)[Maarath]], #underline[#myhl(places)[Beth-anoth]], and #underline[#myhl(places)[Eltekon]]: #myhl(numbers)[six] cities with their villages.


  
#versenum(60) #myhl(places)[Kiriath-baal] (that is, #myhl(places)[Kiriath-jearim]), and #myhl(places)[Rabbah]: #myhl(numbers)[two] cities with their villages.


  
#versenum(61) In the wilderness, #myhl(places)[Beth-arabah], #underline[#myhl(places)[Middin]], #underline[#myhl(places)[Secacah]], 
#versenum(62) #underline[#myhl(places)[Nibshan]], the #myhl(places)[City of Salt], and #underline[#myhl(places)[Engedi]]: #myhl(numbers)[six] cities with their villages.


  
#versenum(63) But the #myhl(other)[Jebusites], the inhabitants of #myhl(places)[Jerusalem], the people of #myhl(other)[Judah] could not drive out, so the #myhl(other)[Jebusites] dwell with the people of #myhl(other)[Judah] at #myhl(places)[Jerusalem] to this day.


  
#chapter-heading[Joshua 16]


#section-heading[The Allotment for Ephraim and Manasseh]


#versenum(1) The allotment of the people of #myhl(men)[Joseph] went from the #myhl(places)[Jordan] by #myhl(places)[Jericho], east of the waters of #myhl(places)[Jericho], into the wilderness, going up from #myhl(places)[Jericho] into the hill country to #myhl(places)[Bethel]. 
#versenum(2) Then going from #myhl(places)[Bethel] to #myhl(places)[Luz], it passes along to #myhl(places)[Ataroth], the territory of the #underline[#myhl(other)[Archites]]. 
#versenum(3) Then it goes down westward to the territory of the #underline[#myhl(other)[Japhletites]], as far as the territory of #myhl(places)[Lower Beth-horon], then to #myhl(places)[Gezer], and it ends at the sea.


  
#versenum(4) The people of #myhl(men)[Joseph], #myhl(other)[Manasseh] and #myhl(other)[Ephraim], received their inheritance.


  
#versenum(5) The territory of the people of #myhl(other)[Ephraim] by their clans was as #underline[follows]: the boundary of their inheritance on the east was #myhl(places)[Ataroth-addar] as far as #myhl(places)[Upper Beth-horon], 
#versenum(6) and the boundary goes from there to the sea. On the north is #myhl(places)[Michmethath]. Then on the east the boundary turns around toward #underline[#myhl(places)[Taanath-shiloh]] and passes along beyond it on the east to #myhl(places)[Janoah], 
#versenum(7) then it goes down from #myhl(places)[Janoah] to #myhl(places)[Ataroth] and to #underline[#myhl(places)[Naarah]], and touches #myhl(places)[Jericho], #underline[ending] at the #myhl(places)[Jordan]. 
#versenum(8) From #myhl(places)[Tappuah] the boundary goes westward to the brook #myhl(places)[Kanah] and ends at the sea. Such is the inheritance of the tribe of the people of #myhl(other)[Ephraim] by their clans, 
#versenum(9) together with the towns that were set apart for the people of #myhl(other)[Ephraim] within the inheritance of the #underline[#myhl(other)[Manassites]], all those towns with their villages. 
#versenum(10) #underline[However], they did not drive out the #myhl(other)[Canaanites] who lived in #myhl(places)[Gezer], so the #myhl(other)[Canaanites] have lived in the midst of #myhl(other)[Ephraim] to this day but have been made to do forced labor.


  
#chapter-heading[Joshua 17]


#versenum(1) Then allotment was made to the people of #myhl(other)[Manasseh], for he was the firstborn of #myhl(men)[Joseph]. To #myhl(men)[Machir] the firstborn of #myhl(men)[Manasseh], the father of #myhl(men)[Gilead], were allotted #myhl(places)[Gilead] and #myhl(places)[Bashan], because he was a man of war. 
#versenum(2) And allotments were made to the rest of the people of #myhl(other)[Manasseh] by their clans, #myhl(men)[Abiezer], #underline[#myhl(men)[Helek]], #underline[#myhl(men)[Asriel]], #myhl(places)[Shechem], #myhl(men)[Hepher], and #underline[#myhl(men)[Shemida]]. These were the male descendants of #myhl(men)[Manasseh] the son of #myhl(men)[Joseph], by their clans.


  
#versenum(3) Now #underline[#myhl(men)[Zelophehad]] the son of #myhl(men)[Hepher], son of #myhl(men)[Gilead], son of #myhl(men)[Machir], son of #myhl(men)[Manasseh], had no sons, but only daughters, and these are the names of his daughters: #underline[#myhl(women)[Mahlah]], #underline[#myhl(women)[Noah]], #underline[#myhl(women)[Hoglah]], #underline[#myhl(women)[Milcah]], and #myhl(women)[Tirzah]. 
#versenum(4) They #underline[approached] #myhl(men)[Eleazar] the priest and #myhl(men)[Joshua] the son of #myhl(men)[Nun] and the leaders and said, “The #myhl(divine)[#smallcaps[Lord]] commanded #myhl(men)[Moses] to give us an inheritance along with our brothers.” So according to the mouth of the #myhl(divine)[#smallcaps[Lord]] he gave them an inheritance among the brothers of their father. 
#versenum(5) Thus there fell to #myhl(other)[Manasseh] #myhl(numbers)[ten] portions, besides the land of #myhl(places)[Gilead] and #myhl(places)[Bashan], which is on the other side of the #myhl(places)[Jordan], 
#versenum(6) because the daughters of #myhl(other)[Manasseh] received an inheritance along with his sons. The land of #myhl(places)[Gilead] was allotted to the rest of the people of #myhl(other)[Manasseh].


  
#versenum(7) The territory of #myhl(places)[Manasseh] reached from #myhl(places)[Asher] to #myhl(places)[Michmethath], which is east of #myhl(places)[Shechem]. Then the boundary goes along southward to the inhabitants of #underline[#myhl(places)[En-tappuah]]. 
#versenum(8) The land of #myhl(places)[Tappuah] belonged to #myhl(other)[Manasseh], but the town of #myhl(places)[Tappuah] on the boundary of #myhl(places)[Manasseh] belonged to the people of #myhl(other)[Ephraim]. 
#versenum(9) Then the boundary went down to the brook #myhl(places)[Kanah]. These cities, to the south of the brook, among the cities of #myhl(places)[Manasseh], belong to #myhl(other)[Ephraim]. Then the boundary of #myhl(places)[Manasseh] goes on the north side of the brook and ends at the sea, 
#versenum(10) the land to the south being #underline[#myhl(other)[Ephraim]’s] and that to the north being #underline[#myhl(other)[Manasseh]’s], with the sea #underline[forming] its boundary. On the north #myhl(places)[Asher] is reached, and on the east #myhl(places)[Issachar]. 
#versenum(11) Also in #myhl(places)[Issachar] and in #myhl(places)[Asher] #myhl(other)[Manasseh] had #myhl(places)[Beth-shean] and its villages, and #myhl(places)[Ibleam] and its villages, and the inhabitants of #myhl(places)[Dor] and its villages, and the inhabitants of #underline[#myhl(places)[En-dor]] and its villages, and the inhabitants of #myhl(places)[Taanach] and its villages, and the inhabitants of #myhl(places)[Megiddo] and its villages; the #myhl(numbers)[third] is #underline[#myhl(places)[Naphath]].#footnote[Joshua 17:11 The meaning of the Hebrew is uncertain] 
#versenum(12) Yet the people of #myhl(other)[Manasseh] could not take possession of those cities, but the #myhl(other)[Canaanites] persisted in dwelling in that land. 
#versenum(13) Now when the #myhl(people-groups)[people of Israel] grew strong, they put the #myhl(other)[Canaanites] to forced labor, but did not utterly drive them out.


  
#versenum(14) Then the people of #myhl(men)[Joseph] spoke to #myhl(men)[Joshua], saying, “Why have you given me but one lot and one portion as an inheritance, although I am a numerous people, since all along the #myhl(divine)[#smallcaps[Lord]] has blessed me?” 
#versenum(15) And #myhl(men)[Joshua] said to them, “If you are a numerous people, go up by yourselves to the forest, and there clear ground for yourselves in the land of the #myhl(other)[Perizzites] and the #myhl(other)[Rephaim], since #myhl(places)[the hill country of Ephraim] is too #underline[narrow] for you.” 
#versenum(16) The people of #myhl(men)[Joseph] said, “The hill country is not enough for us. Yet all the #myhl(other)[Canaanites] who dwell in the plain have chariots of iron, both those in #myhl(places)[Beth-shean] and its villages and those in the #myhl(places)[Valley of Jezreel].” 
#versenum(17) Then #myhl(men)[Joshua] said to the house of #myhl(men)[Joseph], to #myhl(other)[Ephraim] and #myhl(other)[Manasseh], “You are a numerous people and have great power. You shall not have one allotment only, 
#versenum(18) but the hill country shall be yours, for though it is a forest, you shall clear it and possess it to its farthest #underline[borders]. For you shall drive out the #myhl(other)[Canaanites], though they have chariots of iron, and though they are strong.”


  
#chapter-heading[Joshua 18]


#section-heading[Allotment of the Remaining Land]


#versenum(1) Then the whole congregation of the #myhl(people-groups)[people of Israel] assembled at #myhl(places)[Shiloh] and set up the tent of meeting there. The land lay subdued before them.


  
#versenum(2) There remained among the #myhl(people-groups)[people of Israel] #myhl(numbers)[seven] tribes whose inheritance had not yet been apportioned. 
#versenum(3) So #myhl(men)[Joshua] said to the #myhl(people-groups)[people of Israel], “How long will you put off going in to take possession of the land, which the #myhl(divine)[#smallcaps[Lord], the God] of your fathers, has given you? 
#versenum(4) #underline[Provide] #myhl(numbers)[three] men from each tribe, and I will send them out that they may set out and go up and down the land. They shall write a description of it with a view to their inheritances, and then come to me. 
#versenum(5) They shall divide it into #myhl(numbers)[seven] portions. #myhl(other)[Judah] shall continue in his territory on the south, and the house of #myhl(men)[Joseph] shall continue in their territory on the north. 
#versenum(6) And you shall #underline[describe] the land in #myhl(numbers)[seven] divisions and bring the description here to me. And I will cast lots for you here before the #myhl(divine)[#smallcaps[Lord]] our #myhl(divine)[God]. 
#versenum(7) The #myhl(other)[Levites] have no portion among you, for the #underline[priesthood] of the #myhl(divine)[#smallcaps[Lord]] is their #underline[heritage]. And #myhl(other)[Gad] and #myhl(other)[Reuben] and #myhl(numbers)[half] the tribe of #myhl(other)[Manasseh] have received their inheritance beyond the #myhl(places)[Jordan] eastward, which #myhl(men)[Moses] the servant of the #myhl(divine)[#smallcaps[Lord]] gave them.”


  
#versenum(8) So the men arose and went, and #myhl(men)[Joshua] charged those who went to write the description of the land, saying, “Go up and down in the land and write a description and return to me. And I will cast lots for you here before the #myhl(divine)[#smallcaps[Lord]] in #myhl(places)[Shiloh].” 
#versenum(9) So the men went and passed up and down in the land and wrote in a book a description of it by towns in #myhl(numbers)[seven] divisions. Then they came to #myhl(men)[Joshua] to the camp at #myhl(places)[Shiloh], 
#versenum(10) and #myhl(men)[Joshua] cast lots for them in #myhl(places)[Shiloh] before the #myhl(divine)[#smallcaps[Lord]]. And there #myhl(men)[Joshua] apportioned the land to the #myhl(people-groups)[people of Israel], to each his portion.


  
#section-heading[The Inheritance for Benjamin]


#versenum(11) The lot of the tribe of the people of #myhl(men)[Benjamin] according to its clans came up, and the territory allotted to it fell between the people of #myhl(other)[Judah] and the people of #myhl(men)[Joseph]. 
#versenum(12) On the north side their boundary began at the #myhl(places)[Jordan]. Then the boundary goes up to the shoulder north of #myhl(places)[Jericho], then up through the hill country westward, and it ends at the wilderness of #myhl(places)[Beth-aven]. 
#versenum(13) From there the boundary passes along southward in the direction of #myhl(places)[Luz], to the shoulder of #myhl(places)[Luz] (that is, #myhl(places)[Bethel]), then the boundary goes down to #myhl(places)[Ataroth-addar], on the mountain that lies south of #myhl(places)[Lower Beth-horon]. 
#versenum(14) Then the boundary goes in another direction, turning on the western side southward from the mountain that lies to the south, opposite #myhl(places)[Beth-horon], and it ends at #myhl(places)[Kiriath-baal] (that is, #myhl(places)[Kiriath-jearim]), a city belonging to the people of #myhl(other)[Judah]. This forms the western side. 
#versenum(15) And the southern side #underline[begins] at the outskirts of #myhl(places)[Kiriath-jearim]. And the boundary goes from there to #myhl(places)[Ephron],#footnote[Joshua 18:15 See 15:9; Hebrew #emph[westward]] to the spring of the waters of #myhl(places)[Nephtoah]. 
#versenum(16) Then the boundary goes down to the border of the mountain that #underline[overlooks] the #myhl(places)[Valley of the Son of Hinnom], which is at the north end of the #myhl(places)[Valley of Rephaim]. And it then goes down the #myhl(places)[Valley of Hinnom], south of the shoulder of the #myhl(other)[Jebusites], and #underline[downward] to #myhl(places)[En-rogel]. 
#versenum(17) Then it bends in a #underline[northerly] direction going on to #myhl(places)[En-shemesh], and from there goes to #underline[#myhl(places)[Geliloth]], which is opposite the ascent of #myhl(places)[Adummim]. Then it goes down to the stone of #myhl(men)[Bohan] the son of #myhl(men)[Reuben], 
#versenum(18) and passing on to the north of the shoulder of #myhl(places)[Beth-arabah]#footnote[Joshua 18:18 Septuagint; Hebrew #emph[to the shoulder over against the Arabah]] it goes down to the #myhl(places)[Arabah]. 
#versenum(19) Then the boundary passes on to the north of the shoulder of #myhl(places)[Beth-hoglah]. And the boundary ends at the northern bay of the #myhl(places)[Salt Sea], at the south end of the #myhl(places)[Jordan]: this is the southern border. 
#versenum(20) The #myhl(places)[Jordan] forms its boundary on the #underline[eastern] side. This is the inheritance of the people of #myhl(men)[Benjamin], according to their clans, boundary by boundary all around.


  
#versenum(21) Now the cities of the tribe of the people of #myhl(men)[Benjamin] according to their clans were #myhl(places)[Jericho], #myhl(places)[Beth-hoglah], #underline[#myhl(places)[Emek-keziz]], 
#versenum(22) #myhl(places)[Beth-arabah], #underline[#myhl(places)[Zemaraim]], #myhl(places)[Bethel], 
#versenum(23) #myhl(places)[Avvim], #underline[#myhl(places)[Parah]], #myhl(places)[Ophrah], 
#versenum(24) #underline[#myhl(places)[Chephar-ammoni]], #underline[#myhl(places)[Ophni]], #myhl(places)[Geba]—#myhl(numbers)[twelve] cities with their villages: 
#versenum(25) #myhl(places)[Gibeon], #myhl(places)[Ramah], #myhl(places)[Beeroth], 
#versenum(26) #myhl(places)[Mizpeh], #myhl(places)[Chephirah], #underline[#myhl(places)[Mozah]], 
#versenum(27) #myhl(places)[Rekem], #underline[#myhl(places)[Irpeel]], #underline[#myhl(places)[Taralah]], 
#versenum(28) #underline[#myhl(places)[Zela]], #underline[#myhl(places)[Haeleph]], #myhl(places)[Jebus]#footnote[Joshua 18:28 Septuagint, Syriac, Vulgate; Hebrew #emph[the Jebusite]] (that is, #myhl(places)[Jerusalem]), #myhl(places)[Gibeah]#footnote[Joshua 18:28 Hebrew #emph[Gibeath]] and #myhl(places)[Kiriath-jearim]#footnote[Joshua 18:28 Septuagint; Hebrew #emph[Kiriath]]—#myhl(numbers)[fourteen] cities with their villages. This is the inheritance of the people of #myhl(men)[Benjamin] according to its clans.


  
#chapter-heading[Joshua 19]


#section-heading[The Inheritance for Simeon]


#versenum(1) The #myhl(numbers)[second] lot came out for #myhl(men)[Simeon], for the tribe of the people of #myhl(men)[Simeon], according to their clans, and their inheritance was in the midst of the inheritance of the people of #myhl(other)[Judah]. 
#versenum(2) And they had for their inheritance #myhl(places)[Beersheba], #underline[#myhl(places)[Sheba]], #myhl(places)[Moladah], 
#versenum(3) #myhl(places)[Hazar-shual], #underline[#myhl(places)[Balah]], #myhl(places)[Ezem], 
#versenum(4) #myhl(places)[Eltolad], #underline[#myhl(places)[Bethul]], #myhl(places)[Hormah], 
#versenum(5) #myhl(places)[Ziklag], #underline[#myhl(places)[Beth-marcaboth]], #underline[#myhl(places)[Hazar-susah]], 
#versenum(6) #underline[#myhl(places)[Beth-lebaoth]], and #underline[#myhl(places)[Sharuhen]]—#myhl(numbers)[thirteen] cities with their villages; 
#versenum(7) #myhl(places)[Ain], #myhl(places)[Rimmon], #myhl(places)[Ether], and #myhl(places)[Ashan]—#myhl(numbers)[four] cities with their villages, 
#versenum(8) together with all the villages around these cities as far as #underline[#myhl(places)[Baalath-beer]], #myhl(places)[Ramah] of the #myhl(places)[Negeb]. This was the inheritance of the tribe of the people of #myhl(men)[Simeon] according to their clans. 
#versenum(9) The inheritance of the people of #myhl(men)[Simeon] formed part of the territory of the people of #myhl(other)[Judah]. Because the portion of the people of #myhl(other)[Judah] was too large for them, the people of #myhl(men)[Simeon] #underline[obtained] an inheritance in the midst of their inheritance.


  
#section-heading[The Inheritance for Zebulun]


#versenum(10) The #myhl(numbers)[third] lot came up for the people of #myhl(other)[Zebulun], according to their clans. And the territory of their inheritance reached as far as #myhl(places)[Sarid]. 
#versenum(11) Then their boundary goes up westward and on to #underline[#myhl(places)[Mareal]] and touches #underline[#myhl(places)[Dabbesheth]], then the brook that is east of #myhl(places)[Jokneam]. 
#versenum(12) From #myhl(places)[Sarid] it goes in the other direction eastward toward the sunrise to the boundary of #underline[#myhl(places)[Chisloth-tabor]]. From there it goes to #myhl(places)[Daberath], then up to #myhl(places)[Japhia]. 
#versenum(13) From there it passes along on the east toward the sunrise to #underline[#myhl(places)[Gath-hepher]], to #underline[#myhl(places)[Eth-kazin]], and going on to #myhl(places)[Rimmon] it bends toward #underline[#myhl(places)[Neah]], 
#versenum(14) then on the north the boundary turns about to #underline[#myhl(places)[Hannathon]], and it ends at the #myhl(places)[Valley of Iphtahel]; 
#versenum(15) and #underline[#myhl(places)[Kattath]], #myhl(places)[Nahalal], #myhl(places)[Shimron], #underline[#myhl(places)[Idalah]], and #myhl(places)[Bethlehem]—#myhl(numbers)[twelve] cities with their villages. 
#versenum(16) This is the inheritance of the people of #myhl(other)[Zebulun], according to their clans—these cities with their villages.


  
#section-heading[The Inheritance for Issachar]


#versenum(17) The #myhl(numbers)[fourth] lot came out for #myhl(other)[Issachar], for the people of #myhl(other)[Issachar], according to their clans. 
#versenum(18) Their territory included #myhl(places)[Jezreel], #underline[#myhl(places)[Chesulloth]], #underline[#myhl(places)[Shunem]], 
#versenum(19) #underline[#myhl(places)[Hapharaim]], #underline[#myhl(places)[Shion]], #underline[#myhl(places)[Anaharath]], 
#versenum(20) #underline[#myhl(places)[Rabbith]], #myhl(places)[Kishion], #underline[#myhl(places)[Ebez]], 
#versenum(21) #underline[#myhl(places)[Remeth]], #myhl(places)[En-gannim], #underline[#myhl(places)[En-haddah]], #underline[#myhl(places)[Beth-pazzez]]. 
#versenum(22) The boundary also touches #myhl(places)[Tabor], #underline[#myhl(places)[Shahazumah]], and #myhl(places)[Beth-shemesh], and its boundary ends at the #myhl(places)[Jordan]—#myhl(numbers)[sixteen] cities with their villages. 
#versenum(23) This is the inheritance of the tribe of the people of #myhl(other)[Issachar], according to their clans—the cities with their villages.


  
#section-heading[The Inheritance for Asher]


#versenum(24) The #myhl(numbers)[fifth] lot came out for the tribe of the people of #myhl(other)[Asher] according to their clans. 
#versenum(25) Their territory included #myhl(places)[Helkath], #underline[#myhl(places)[Hali]], #underline[#myhl(places)[Beten]], #myhl(places)[Achshaph], 
#versenum(26) #underline[#myhl(places)[Allammelech]], #underline[#myhl(places)[Amad]], and #myhl(places)[Mishal]. On the west it touches #myhl(places)[Carmel] and #underline[#myhl(places)[Shihor-libnath]], 
#versenum(27) then it turns eastward, it goes to #myhl(places)[Beth-dagon], and touches #myhl(other)[Zebulun] and the #myhl(places)[Valley of Iphtahel] northward to #underline[#myhl(places)[Beth-emek]] and #underline[#myhl(places)[Neiel]]. Then it #underline[continues] in the north to #underline[#myhl(places)[Cabul]], 
#versenum(28) #underline[#myhl(places)[Ebron]], #myhl(places)[Rehob], #underline[#myhl(places)[Hammon]], #myhl(places)[Kanah], as far as #myhl(places)[Sidon] the Great. 
#versenum(29) Then the boundary turns to #myhl(places)[Ramah], #underline[reaching] to the fortified city of #underline[#myhl(places)[Tyre]]. Then the boundary turns to #underline[#myhl(places)[Hosah]], and it ends at the sea; #underline[#myhl(places)[Mahalab]],#footnote[Joshua 19:29 Compare Septuagint; Hebrew #emph[Mehebel]] #myhl(places)[Achzib], 
#versenum(30) #underline[#myhl(places)[Ummah]], #myhl(places)[Aphek] and #myhl(places)[Rehob]—#myhl(numbers)[twenty-two] cities with their villages. 
#versenum(31) This is the inheritance of the tribe of the people of #myhl(other)[Asher] according to their clans—these cities with their villages.


  
#section-heading[The Inheritance for Naphtali]


#versenum(32) The #underline[#myhl(numbers)[sixth]] lot came out for the people of #myhl(other)[Naphtali], for the people of #myhl(other)[Naphtali], according to their clans. 
#versenum(33) And their boundary ran from #underline[#myhl(places)[Heleph]], from the oak in #myhl(places)[Zaanannim], and #underline[#myhl(places)[Adami-nekeb]], and #myhl(places)[Jabneel], as far as #underline[#myhl(places)[Lakkum]], and it #underline[ended] at the #myhl(places)[Jordan]. 
#versenum(34) Then the boundary turns westward to #underline[#myhl(places)[Aznoth-tabor]] and goes from there to #underline[#myhl(places)[Hukkok]], #underline[touching] #myhl(other)[Zebulun] at the south and #myhl(other)[Asher] on the west and #myhl(other)[Judah] on the east at the #myhl(places)[Jordan]. 
#versenum(35) The fortified cities are #underline[#myhl(places)[Ziddim]], #underline[#myhl(places)[Zer]], #underline[#myhl(places)[Hammath]], #underline[#myhl(places)[Rakkath]], #myhl(places)[Chinnereth], 
#versenum(36) #underline[#myhl(places)[Adamah]], #myhl(places)[Ramah], #myhl(places)[Hazor], 
#versenum(37) #myhl(places)[Kedesh], #myhl(places)[Edrei], #underline[#myhl(places)[En-hazor]], 
#versenum(38) #underline[#myhl(places)[Yiron]], #underline[#myhl(places)[Migdal-el]], #underline[#myhl(places)[Horem]], #myhl(places)[Beth-anath], and #myhl(places)[Beth-shemesh]—#underline[#myhl(numbers)[nineteen]] cities with their villages. 
#versenum(39) This is the inheritance of the tribe of the people of #myhl(other)[Naphtali] according to their clans—the cities with their villages.


  
#section-heading[The Inheritance for Dan]


#versenum(40) The #myhl(numbers)[seventh] lot came out for the tribe of the people of #myhl(other)[Dan], according to their clans. 
#versenum(41) And the territory of its inheritance included #myhl(places)[Zorah], #myhl(places)[Eshtaol], #underline[#myhl(places)[Ir-shemesh]], 
#versenum(42) #underline[#myhl(places)[Shaalabbin]], #myhl(places)[Aijalon], #underline[#myhl(places)[Ithlah]], 
#versenum(43) #myhl(places)[Elon], #myhl(places)[Timnah], #myhl(places)[Ekron], 
#versenum(44) #underline[#myhl(places)[Eltekeh]], #myhl(places)[Gibbethon], #underline[#myhl(places)[Baalath]], 
#versenum(45) #underline[#myhl(places)[Jehud]], #underline[#myhl(places)[Bene-berak]], #myhl(places)[Gath-rimmon], 
#versenum(46) and #underline[#myhl(places)[Me-jarkon]] and #underline[#myhl(places)[Rakkon]] with the territory over against #underline[#myhl(places)[Joppa]]. 
#versenum(47) When the territory of the people of #myhl(other)[Dan] was #underline[lost] to them, the people of #myhl(other)[Dan] went up and fought against #myhl(places)[Leshem], and after #underline[capturing] it and striking it with the sword they took possession of it and settled in it, #underline[calling] #myhl(places)[Leshem], #myhl(other)[Dan], after the name of #myhl(other)[Dan] their ancestor. 
#versenum(48) This is the inheritance of the tribe of the people of #myhl(other)[Dan], according to their clans—these cities with their villages.


  
#section-heading[The Inheritance for Joshua]


#versenum(49) When they had finished #underline[distributing] the several #underline[territories] of the land as inheritances, the #myhl(people-groups)[people of Israel] gave an inheritance among them to #myhl(men)[Joshua] the son of #myhl(men)[Nun]. 
#versenum(50) By command of the #myhl(divine)[#smallcaps[Lord]] they gave him the city that he asked, #myhl(places)[Timnath-serah] in #myhl(places)[the hill country of Ephraim]. And he rebuilt the city and settled in it.


  
#versenum(51) These are the inheritances that #myhl(men)[Eleazar] the priest and #myhl(men)[Joshua] the son of #myhl(men)[Nun] and the heads of the fathers’ houses of the tribes of the #myhl(people-groups)[people of Israel] distributed by lot at #myhl(places)[Shiloh] before the #myhl(divine)[#smallcaps[Lord]], at the entrance of the tent of meeting. So they finished #underline[dividing] the land.


  
#chapter-heading[Joshua 20]


#section-heading[The Cities of Refuge]


#versenum(1) Then the #myhl(divine)[#smallcaps[Lord]] said to #myhl(men)[Joshua], 
#versenum(2) “Say to the #myhl(people-groups)[people of Israel], ‘#underline[Appoint] the cities of refuge, of which I spoke to you through #myhl(men)[Moses], 
#versenum(3) that the manslayer who #underline[strikes] any person without intent or unknowingly may flee there. They shall be for you a refuge from the avenger of blood. 
#versenum(4) He shall flee to one of these cities and shall stand at the entrance of the gate of the city and #underline[explain] his #underline[case] to the elders of that city. Then they shall take him into the city and give him a place, and he shall remain with them. 
#versenum(5) And if the avenger of blood #underline[pursues] him, they shall not give up the manslayer into his hand, because he struck his #underline[neighbor] unknowingly, and did not hate him in the #underline[past]. 
#versenum(6) And he shall remain in that city until he has stood before the congregation for judgment, until the death of him who is #underline[high] priest at the time. Then the manslayer may return to his own town and his own home, to the town from which he fled.’”


  
#versenum(7) So they set apart #myhl(places)[Kedesh] in #myhl(places)[Galilee] in #myhl(places)[the hill country of Naphtali], and #myhl(places)[Shechem] in #myhl(places)[the hill country of Ephraim], and #myhl(places)[Kiriath-arba] (that is, #myhl(places)[Hebron]) in #myhl(places)[the hill country of Judah]. 
#versenum(8) And beyond the #myhl(places)[Jordan] east of #myhl(places)[Jericho], they appointed #myhl(places)[Bezer] in the wilderness on the tableland, from the tribe of #myhl(other)[Reuben], and #myhl(places)[Ramoth] in #myhl(places)[Gilead], from the tribe of #myhl(other)[Gad], and #myhl(places)[Golan] in #myhl(places)[Bashan], from the tribe of #myhl(other)[Manasseh]. 
#versenum(9) These were the cities #underline[designated] for all the #myhl(people-groups)[people of Israel] and for the #underline[stranger] sojourning among them, that anyone who killed a person without intent could flee there, so that he might not die by the hand of the avenger of blood, till he stood before the congregation.


  
#chapter-heading[Joshua 21]


#section-heading[Cities and Pasturelands Allotted to Levi]


#versenum(1) Then the heads of the fathers’ houses of the #myhl(other)[Levites] came to #myhl(men)[Eleazar] the priest and to #myhl(men)[Joshua] the son of #myhl(men)[Nun] and to the heads of the fathers’ houses of the tribes of the #myhl(people-groups)[people of Israel]. 
#versenum(2) And they said to them at #myhl(places)[Shiloh] in the land of #myhl(places)[Canaan], “The #myhl(divine)[#smallcaps[Lord]] commanded through #myhl(men)[Moses] that we be given cities to dwell in, along with their pasturelands for our livestock.” 
#versenum(3) So by command of the #myhl(divine)[#smallcaps[Lord]] the #myhl(people-groups)[people of Israel] gave to the #myhl(other)[Levites] the following cities and pasturelands out of their inheritance.


  
#versenum(4) The lot came out for the clans of the #myhl(other)[Kohathites]. So those #myhl(other)[Levites] who were descendants of #myhl(men)[Aaron] the priest received by lot from the tribes of #myhl(other)[Judah], #myhl(men)[Simeon], and #myhl(men)[Benjamin], #myhl(numbers)[thirteen] cities.


  
#versenum(5) And the rest of the #myhl(other)[Kohathites] received by lot from the clans of the tribe of #myhl(other)[Ephraim], from the tribe of #myhl(other)[Dan] and the #myhl(numbers)[half]-tribe of #myhl(other)[Manasseh], #myhl(numbers)[ten] cities.


  
#versenum(6) The #myhl(other)[Gershonites] received by lot from the clans of the tribe of #myhl(other)[Issachar], from the tribe of #myhl(other)[Asher], from the tribe of #myhl(other)[Naphtali], and from the #myhl(numbers)[half]-tribe of #myhl(other)[Manasseh] in #myhl(places)[Bashan], #myhl(numbers)[thirteen] cities.


  
#versenum(7) The #underline[#myhl(other)[Merarites]] according to their clans received from the tribe of #myhl(other)[Reuben], the tribe of #myhl(other)[Gad], and the tribe of #myhl(other)[Zebulun], #myhl(numbers)[twelve] cities.


  
#versenum(8) These cities and their pasturelands the #myhl(people-groups)[people of Israel] gave by lot to the #myhl(other)[Levites], as the #myhl(divine)[#smallcaps[Lord]] had commanded through #myhl(men)[Moses].


  
#versenum(9) Out of the tribe of the people of #myhl(other)[Judah] and the tribe of the people of #myhl(men)[Simeon] they gave the following cities #underline[mentioned] by name, 
#versenum(10) which went to the descendants of #myhl(men)[Aaron], one of the clans of the #myhl(other)[Kohathites] who belonged to the people of #myhl(other)[Levi]; since the lot fell to them #myhl(numbers)[first]. 
#versenum(11) They gave them #myhl(places)[Kiriath-arba] (#myhl(men)[Arba] being the father of #myhl(men)[Anak]), that is #myhl(places)[Hebron], in #myhl(places)[the hill country of Judah], along with the pasturelands around it. 
#versenum(12) But the fields of the city and its villages had been given to #myhl(men)[Caleb] the son of #myhl(men)[Jephunneh] as his possession.


  
#versenum(13) And to the descendants of #myhl(men)[Aaron] the priest they gave #myhl(places)[Hebron], the city of refuge for the manslayer, with its pasturelands, #myhl(places)[Libnah] with its pasturelands, 
#versenum(14) #myhl(places)[Jattir] with its pasturelands, #underline[#myhl(places)[Eshtemoa]] with its pasturelands, 
#versenum(15) #myhl(places)[Holon] with its pasturelands, #myhl(places)[Debir] with its pasturelands, 
#versenum(16) #myhl(places)[Ain] with its pasturelands, #myhl(places)[Juttah] with its pasturelands, #myhl(places)[Beth-shemesh] with its pasturelands—#myhl(numbers)[nine] cities out of these #myhl(numbers)[two] tribes; 
#versenum(17) then out of the tribe of #myhl(men)[Benjamin], #myhl(places)[Gibeon] with its pasturelands, #myhl(places)[Geba] with its pasturelands, 
#versenum(18) #underline[#myhl(places)[Anathoth]] with its pasturelands, and #underline[#myhl(places)[Almon]] with its pasturelands—#myhl(numbers)[four] cities. 
#versenum(19) The cities of the descendants of #myhl(men)[Aaron], the priests, were in all #myhl(numbers)[thirteen] cities with their pasturelands.


  
#versenum(20) As to the rest of the #myhl(other)[Kohathites] belonging to the #underline[#myhl(other)[Kohathite]] clans of the #myhl(other)[Levites], the cities allotted to them were out of the tribe of #myhl(other)[Ephraim]. 
#versenum(21) To them were given #myhl(places)[Shechem], the city of refuge for the manslayer, with its pasturelands in #myhl(places)[the hill country of Ephraim], #myhl(places)[Gezer] with its pasturelands, 
#versenum(22) #underline[#myhl(other)[Kibzaim]] with its pasturelands, #myhl(places)[Beth-horon] with its pasturelands—#myhl(numbers)[four] cities; 
#versenum(23) and out of the tribe of #myhl(other)[Dan], #underline[#myhl(places)[Elteke]] with its pasturelands, #myhl(places)[Gibbethon] with its pasturelands, 
#versenum(24) #myhl(places)[Aijalon] with its pasturelands, #myhl(places)[Gath-rimmon] with its pasturelands—#myhl(numbers)[four] cities; 
#versenum(25) and out of the #myhl(numbers)[half]-tribe of #myhl(other)[Manasseh], #myhl(places)[Taanach] with its pasturelands, and #myhl(places)[Gath-rimmon] with its pasturelands—#myhl(numbers)[two] cities. 
#versenum(26) The cities of the clans of the rest of the #myhl(other)[Kohathites] were #myhl(numbers)[ten] in all with their pasturelands.


  
#versenum(27) And to the #myhl(other)[Gershonites], one of the clans of the #myhl(other)[Levites], were given out of the #myhl(numbers)[half]-tribe of #myhl(other)[Manasseh], #myhl(places)[Golan] in #myhl(places)[Bashan] with its pasturelands, the city of refuge for the manslayer, and #underline[#myhl(places)[Beeshterah]] with its pasturelands—#myhl(numbers)[two] cities; 
#versenum(28) and out of the tribe of #myhl(other)[Issachar], #myhl(places)[Kishion] with its pasturelands, #myhl(places)[Daberath] with its pasturelands, 
#versenum(29) #myhl(places)[Jarmuth] with its pasturelands, #myhl(places)[En-gannim] with its pasturelands—#myhl(numbers)[four] cities; 
#versenum(30) and out of the tribe of #myhl(other)[Asher], #myhl(places)[Mishal] with its pasturelands, #myhl(men)[Abdon] with its pasturelands, 
#versenum(31) #myhl(places)[Helkath] with its pasturelands, and #myhl(places)[Rehob] with its pasturelands—#myhl(numbers)[four] cities; 
#versenum(32) and out of the tribe of #myhl(other)[Naphtali], #myhl(places)[Kedesh] in #myhl(places)[Galilee] with its pasturelands, the city of refuge for the manslayer, #underline[#myhl(places)[Hammoth-dor]] with its pasturelands, and #underline[#myhl(places)[Kartan]] with its pasturelands—#myhl(numbers)[three] cities. 
#versenum(33) The cities of the several clans of the #myhl(other)[Gershonites] were in all #myhl(numbers)[thirteen] cities with their pasturelands.


  
#versenum(34) And to the rest of the #myhl(other)[Levites], the #myhl(other)[Merarite] clans, were given out of the tribe of #myhl(other)[Zebulun], #myhl(places)[Jokneam] with its pasturelands, #underline[#myhl(places)[Kartah]] with its pasturelands, 
#versenum(35) #underline[#myhl(places)[Dimnah]] with its pasturelands, #myhl(places)[Nahalal] with its pasturelands—#myhl(numbers)[four] cities; 
#versenum(36) and out of the tribe of #myhl(other)[Reuben], #myhl(places)[Bezer] with its pasturelands, #myhl(places)[Jahaz] with its pasturelands, 
#versenum(37) #myhl(places)[Kedemoth] with its pasturelands, and #myhl(places)[Mephaath] with its pasturelands—#myhl(numbers)[four] cities; 
#versenum(38) and out of the tribe of #myhl(other)[Gad], #myhl(places)[Ramoth] in #myhl(places)[Gilead] with its pasturelands, the city of refuge for the manslayer, #myhl(places)[Mahanaim] with its pasturelands, 
#versenum(39) #myhl(places)[Heshbon] with its pasturelands, #myhl(places)[Jazer] with its pasturelands—#myhl(numbers)[four] cities in all. 
#versenum(40) As for the cities of the several #myhl(other)[Merarite] clans, that is, the #underline[remainder] of the clans of the #myhl(other)[Levites], those allotted to them were in all #myhl(numbers)[twelve] cities.


  
#versenum(41) The cities of the #myhl(other)[Levites] in the midst of the possession of the #myhl(people-groups)[people of Israel] were in all #underline[#myhl(numbers)[forty-eight]] cities with their pasturelands. 
#versenum(42) These cities each had its pasturelands around it. So it was with all these cities.


  
#versenum(43) Thus the #myhl(divine)[#smallcaps[Lord]] gave to #myhl(people-groups)[Israel] all the land that he swore to give to their fathers. And they took possession of it, and they settled there. 
#versenum(44) And the #myhl(divine)[#smallcaps[Lord]] gave them rest on every side just as he had sworn to their fathers. Not one of all their enemies had #underline[withstood] them, for the #myhl(divine)[#smallcaps[Lord]] had given all their enemies into their hands. 
#versenum(45) Not one word of all the good #underline[promises] that the #myhl(divine)[#smallcaps[Lord]] had made to the #myhl(people-groups)[house of Israel] had failed; all came to pass.


  
#chapter-heading[Joshua 22]


#section-heading[The Eastern Tribes Return Home]


#versenum(1) At that time #myhl(men)[Joshua] summoned the #myhl(other)[Reubenites] and the #myhl(other)[Gadites] and the #myhl(numbers)[half]-tribe of #myhl(other)[Manasseh], 
#versenum(2) and said to them, “You have kept all that #myhl(men)[Moses] the servant of the #myhl(divine)[#smallcaps[Lord]] commanded you and have obeyed my voice in all that I have commanded you. 
#versenum(3) You have not forsaken your brothers these many days, down to this day, but have been careful to keep the charge of the #myhl(divine)[#smallcaps[Lord] your God]. 
#versenum(4) And now the #myhl(divine)[#smallcaps[Lord] your God] has given rest to your brothers, as he promised them. Therefore turn and go to your tents in the land where your possession lies, which #myhl(men)[Moses] the servant of the #myhl(divine)[#smallcaps[Lord]] gave you on the other side of the #myhl(places)[Jordan]. 
#versenum(5) Only be very careful to observe the commandment and the law that #myhl(men)[Moses] the servant of the #myhl(divine)[#smallcaps[Lord]] commanded you, to love the #myhl(divine)[#smallcaps[Lord] your God], and to walk in all his ways and to keep his commandments and to cling to him and to serve him with all your heart and with all your soul.” 
#versenum(6) So #myhl(men)[Joshua] blessed them and sent them away, and they went to their tents.


  
#versenum(7) Now to the one #myhl(numbers)[half] of the tribe of #myhl(other)[Manasseh] #myhl(men)[Moses] had given a possession in #myhl(places)[Bashan], but to the other #myhl(numbers)[half] #myhl(men)[Joshua] had given a possession beside their brothers in the land west of the #myhl(places)[Jordan]. And when #myhl(men)[Joshua] sent them away to their #underline[homes] and blessed them, 
#versenum(8) he said to them, “Go back to your tents with much wealth and with very much livestock, with silver, gold, bronze, and iron, and with much #underline[clothing]. Divide the spoil of your enemies with your brothers.” 
#versenum(9) So the people of #myhl(other)[Reuben] and the people of #myhl(other)[Gad] and the #myhl(numbers)[half]-tribe of #myhl(other)[Manasseh] returned home, #underline[parting] from the #myhl(people-groups)[people of Israel] at #myhl(places)[Shiloh], which is in the land of #myhl(places)[Canaan], to go to the land of #myhl(places)[Gilead], their own land of which they had #underline[possessed] themselves by command of the #myhl(divine)[#smallcaps[Lord]] through #myhl(men)[Moses].


  
#section-heading[The Eastern Tribes’ Altar of Witness]


#versenum(10) And when they came to the region of the #myhl(places)[Jordan] that is in the land of #myhl(places)[Canaan], the people of #myhl(other)[Reuben] and the people of #myhl(other)[Gad] and the #myhl(numbers)[half]-tribe of #myhl(other)[Manasseh] built there an altar by the #myhl(places)[Jordan], an altar of #underline[imposing] #underline[size]. 
#versenum(11) And the #myhl(people-groups)[people of Israel] heard it said, “Behold, the people of #myhl(other)[Reuben] and the people of #myhl(other)[Gad] and the #myhl(numbers)[half]-tribe of #myhl(other)[Manasseh] have built the altar at the #underline[frontier] of the land of #myhl(places)[Canaan], in the region about the #myhl(places)[Jordan], on the side that belongs to the #myhl(people-groups)[people of Israel].” 
#versenum(12) And when the #myhl(people-groups)[people of Israel] heard of it, the whole assembly of the #myhl(people-groups)[people of Israel] gathered at #myhl(places)[Shiloh] to make war against them.


  
#versenum(13) Then the #myhl(people-groups)[people of Israel] sent to the people of #myhl(other)[Reuben] and the people of #myhl(other)[Gad] and the #myhl(numbers)[half]-tribe of #myhl(other)[Manasseh], in the land of #myhl(places)[Gilead], #myhl(men)[Phinehas] the son of #myhl(men)[Eleazar] the priest, 
#versenum(14) and with him #myhl(numbers)[ten] chiefs, one from each of the tribal families of #myhl(people-groups)[Israel], every one of them the head of a family among the clans of #myhl(people-groups)[Israel]. 
#versenum(15) And they came to the people of #myhl(other)[Reuben], the people of #myhl(other)[Gad], and the #myhl(numbers)[half]-tribe of #myhl(other)[Manasseh], in the land of #myhl(places)[Gilead], and they said to them, 
#versenum(16) “Thus says the whole congregation of the #myhl(divine)[#smallcaps[Lord]], ‘What is this breach of faith that you have committed against the #myhl(divine)[God of Israel] in turning away this day from following the #myhl(divine)[#smallcaps[Lord]] by building yourselves an altar this day in rebellion against the #myhl(divine)[#smallcaps[Lord]]? 
#versenum(17) Have we not had enough of the #underline[sin] at #underline[#myhl(places)[Peor]] from which even yet we have not #underline[cleansed] #underline[ourselves], and for which there came a #underline[plague] upon the congregation of the #myhl(divine)[#smallcaps[Lord]], 
#versenum(18) that you too must turn away this day from following the #myhl(divine)[#smallcaps[Lord]]? And if you too rebel against the #myhl(divine)[#smallcaps[Lord]] today then tomorrow he will be angry with the whole congregation of #myhl(people-groups)[Israel]. 
#versenum(19) But now, if the land of your possession is unclean, pass over into the #myhl(divine)[#smallcaps[Lord]]’s land where the #myhl(divine)[#smallcaps[Lord]]’s tabernacle stands, and take for yourselves a possession among us. Only do not rebel against the #myhl(divine)[#smallcaps[Lord]] or make us as rebels by building for yourselves an altar other than the altar of the #myhl(divine)[#smallcaps[Lord]] our #myhl(divine)[God]. 
#versenum(20) Did not #myhl(men)[Achan] the son of #myhl(men)[Zerah] break faith in the matter of the devoted things, and wrath fell upon all the congregation of #myhl(people-groups)[Israel]? And he did not perish alone for his #underline[iniquity].’”


  
#versenum(21) Then the people of #myhl(other)[Reuben], the people of #myhl(other)[Gad], and the #myhl(numbers)[half]-tribe of #myhl(other)[Manasseh] said in answer to the heads of the families of #myhl(people-groups)[Israel], 
#versenum(22) “The Mighty One, #myhl(divine)[God], the #myhl(divine)[#smallcaps[Lord]]! The Mighty One, #myhl(divine)[God], the #myhl(divine)[#smallcaps[Lord]]! He #underline[knows]; and let #myhl(people-groups)[Israel] itself know! If it was in rebellion or in breach of faith against the #myhl(divine)[#smallcaps[Lord]], do not #underline[spare] us today 
#versenum(23) for building an altar to turn away from following the #myhl(divine)[#smallcaps[Lord]]. Or if we did so to offer burnt offerings or grain offerings or peace offerings on it, may the #myhl(divine)[#smallcaps[Lord]] himself take vengeance. 
#versenum(24) No, but we did it from fear that in time to come your children might say to our children, ‘What have you to do with the #myhl(divine)[#smallcaps[Lord], the God of Israel]? 
#versenum(25) For the #myhl(divine)[#smallcaps[Lord]] has made the #myhl(places)[Jordan] a boundary between us and you, you people of #myhl(other)[Reuben] and people of #myhl(other)[Gad]. You have no portion in the #myhl(divine)[#smallcaps[Lord]].’ So your children might make our children cease to #underline[worship] the #myhl(divine)[#smallcaps[Lord]]. 
#versenum(26) Therefore we said, ‘Let us now build an altar, not for burnt offering, nor for sacrifice, 
#versenum(27) but to be a witness between us and you, and between our generations after us, that we do #underline[perform] the #underline[service] of the #myhl(divine)[#smallcaps[Lord]] in his presence with our burnt offerings and #underline[sacrifices] and peace offerings, so your children will not say to our children in time to come, “You have no portion in the #myhl(divine)[#smallcaps[Lord]].”’ 
#versenum(28) And we thought, ‘If this should be said to us or to our descendants in time to come, we should say, “Behold, the copy of the altar of the #myhl(divine)[#smallcaps[Lord]], which our fathers made, not for burnt offerings, nor for sacrifice, but to be a witness between us and you.”’ 
#versenum(29) Far be it from us that we should rebel against the #myhl(divine)[#smallcaps[Lord]] and turn away this day from following the #myhl(divine)[#smallcaps[Lord]] by building an altar for burnt offering, grain offering, or sacrifice, other than the altar of the #myhl(divine)[#smallcaps[Lord]] our #myhl(divine)[God] that stands before his tabernacle!”


  
#versenum(30) When #myhl(men)[Phinehas] the priest and the chiefs of the congregation, the heads of the families of #myhl(people-groups)[Israel] who were with him, heard the words that the people of #myhl(other)[Reuben] and the people of #myhl(other)[Gad] and the people of #myhl(other)[Manasseh] spoke, it was good in their eyes. 
#versenum(31) And #myhl(men)[Phinehas] the son of #myhl(men)[Eleazar] the priest said to the people of #myhl(other)[Reuben] and the people of #myhl(other)[Gad] and the people of #myhl(other)[Manasseh], “Today we know that the #myhl(divine)[#smallcaps[Lord]] is in our midst, because you have not committed this breach of faith against the #myhl(divine)[#smallcaps[Lord]]. Now you have delivered the #myhl(people-groups)[people of Israel] from the hand of the #myhl(divine)[#smallcaps[Lord]].”


  
#versenum(32) Then #myhl(men)[Phinehas] the son of #myhl(men)[Eleazar] the priest, and the chiefs, returned from the people of #myhl(other)[Reuben] and the people of #myhl(other)[Gad] in the land of #myhl(places)[Gilead] to the land of #myhl(places)[Canaan], to the #myhl(people-groups)[people of Israel], and brought back word to them. 
#versenum(33) And the report was good in the eyes of the #myhl(people-groups)[people of Israel]. And the #myhl(people-groups)[people of Israel] blessed #myhl(divine)[God] and spoke no more of making war against them to destroy the land where the people of #myhl(other)[Reuben] and the people of #myhl(other)[Gad] were settled. 
#versenum(34) The people of #myhl(other)[Reuben] and the people of #myhl(other)[Gad] called the altar Witness, “For,” they said, “it is a witness between us that the #myhl(divine)[#smallcaps[Lord]] is #myhl(divine)[God].”


  
#chapter-heading[Joshua 23]


#section-heading[Joshua’s Charge to Israel’s Leaders]


#versenum(1) A long time afterward, when the #myhl(divine)[#smallcaps[Lord]] had given rest to #myhl(people-groups)[Israel] from all their surrounding enemies, and #myhl(men)[Joshua] was old and well advanced in years, 
#versenum(2) #myhl(men)[Joshua] summoned all #myhl(people-groups)[Israel], its elders and heads, its judges and officers, and said to them, “I am now old and well advanced in years. 
#versenum(3) And you have seen all that the #myhl(divine)[#smallcaps[Lord] your God] has done to all these nations for your sake, for it is the #myhl(divine)[#smallcaps[Lord] your God] who has fought for you. 
#versenum(4) Behold, I have allotted to you as an inheritance for your tribes those nations that remain, along with all the nations that I have already cut off, from the #myhl(places)[Jordan] to the #myhl(places)[Great Sea] in the west. 
#versenum(5) The #myhl(divine)[#smallcaps[Lord] your God] will #underline[push] them back before you and drive them out of your sight. And you shall possess their land, just as the #myhl(divine)[#smallcaps[Lord] your God] promised you. 
#versenum(6) Therefore, be very strong to keep and to do all that is written in the Book of the Law of #myhl(men)[Moses], turning aside from it neither to the right hand nor to the left, 
#versenum(7) that you may not #underline[mix] with these nations remaining among you or make #underline[mention] of the names of their gods or swear by them or serve them or bow down to them, 
#versenum(8) but you shall cling to the #myhl(divine)[#smallcaps[Lord] your God] just as you have done to this day. 
#versenum(9) For the #myhl(divine)[#smallcaps[Lord]] has driven out before you great and strong nations. And as for you, no man has been able to stand before you to this day. 
#versenum(10) One man of you #underline[puts] to #underline[flight] a #myhl(numbers)[thousand], since it is the #myhl(divine)[#smallcaps[Lord] your God] who #underline[fights] for you, just as he promised you. 
#versenum(11) Be very careful, therefore, to love the #myhl(divine)[#smallcaps[Lord] your God]. 
#versenum(12) For if you turn back and cling to the remnant of these nations remaining among you and make #underline[marriages] with them, so that you #underline[associate] with them and they with you, 
#versenum(13) know for certain that the #myhl(divine)[#smallcaps[Lord] your God] will no longer drive out these nations before you, but they shall be a snare and a #underline[trap] for you, a #underline[whip] on your sides and thorns in your eyes, until you perish from off this good ground that the #myhl(divine)[#smallcaps[Lord] your God] has given you.


  
#versenum(14) “And now I am about to go the way of all the earth, and you know in your hearts and #underline[souls], all of you, that not one word has failed of all the good things#footnote[Joshua 23:14 Or #emph[words]; also twice in verse 15] that the #myhl(divine)[#smallcaps[Lord] your God] promised concerning you. All have come to pass for you; not one of them has failed. 
#versenum(15) But just as all the good things that the #myhl(divine)[#smallcaps[Lord] your God] promised concerning you have been #underline[fulfilled] for you, so the #myhl(divine)[#smallcaps[Lord]] will bring upon you all the evil things, until he has destroyed you from off this good land that the #myhl(divine)[#smallcaps[Lord] your God] has given you, 
#versenum(16) if you #underline[transgress] the covenant of the #myhl(divine)[#smallcaps[Lord] your God], which he commanded you, and go and serve other gods and bow down to them. Then the anger of the #myhl(divine)[#smallcaps[Lord]] will be kindled against you, and you shall perish quickly from off the good land that he has given to you.”


  
#chapter-heading[Joshua 24]


#section-heading[The Covenant Renewal at Shechem]


#versenum(1) #myhl(men)[Joshua] gathered all the tribes of #myhl(people-groups)[Israel] to #myhl(places)[Shechem] and summoned the elders, the heads, the judges, and the officers of #myhl(people-groups)[Israel]. And they presented themselves before #myhl(divine)[God]. 
#versenum(2) And #myhl(men)[Joshua] said to all the people, “Thus says the #myhl(divine)[#smallcaps[Lord], the God of Israel], ‘Long #underline[ago], your fathers lived beyond the #myhl(places)[Euphrates],#footnote[Joshua 24:2 Hebrew #emph[the River]] #underline[#myhl(men)[Terah]], the father of #myhl(men)[Abraham] and of #underline[#myhl(men)[Nahor]]; and they served other gods. 
#versenum(3) Then I took your father #myhl(men)[Abraham] from beyond the River#footnote[Joshua 24:3 That is, the Euphrates; also verses 14, 15] and led him through all the land of #myhl(places)[Canaan], and made his offspring many. I gave him #myhl(men)[Isaac]. 
#versenum(4) And to #myhl(men)[Isaac] I gave #myhl(men)[Jacob] and #myhl(men)[Esau]. And I gave #myhl(men)[Esau] #myhl(places)[the hill country of Seir] to possess, but #myhl(men)[Jacob] and his children went down to #myhl(places)[Egypt]. 
#versenum(5) And I sent #myhl(men)[Moses] and #myhl(men)[Aaron], and I #underline[plagued] #myhl(places)[Egypt] with what I did in the midst of it, and afterward I brought you out.


  
#versenum(6) “‘Then I brought your fathers out of #myhl(places)[Egypt], and you came to the sea. And the #myhl(people-groups)[Egyptians] pursued your fathers with chariots and #underline[horsemen] to the #myhl(places)[Red Sea]. 
#versenum(7) And when they cried to the #myhl(divine)[#smallcaps[Lord]], he put #underline[darkness] between you and the #myhl(people-groups)[Egyptians] and made the sea come upon them and #underline[cover] them; and your eyes saw what I did in #myhl(places)[Egypt]. And you lived in the wilderness a long time. 
#versenum(8) Then I brought you to the land of the #myhl(other)[Amorites], who lived on the other side of the #myhl(places)[Jordan]. They fought with you, and I gave them into your hand, and you took possession of their land, and I destroyed them before you. 
#versenum(9) Then #myhl(men)[Balak] the son of #myhl(men)[Zippor], king of #myhl(places)[Moab], arose and fought against #myhl(people-groups)[Israel]. And he sent and invited #myhl(men)[Balaam] the son of #myhl(men)[Beor] to curse you, 
#versenum(10) but I would not listen to #myhl(men)[Balaam]. Indeed, he blessed you. So I delivered you out of his hand. 
#versenum(11) And you went over the #myhl(places)[Jordan] and came to #myhl(places)[Jericho], and the leaders of #myhl(places)[Jericho] fought against you, and also the #myhl(other)[Amorites], the #myhl(other)[Perizzites], the #myhl(other)[Canaanites], the #myhl(other)[Hittites], the #myhl(other)[Girgashites], the #myhl(other)[Hivites], and the #myhl(other)[Jebusites]. And I gave them into your hand. 
#versenum(12) And I sent the #underline[hornet] before you, which drove them out before you, the #myhl(numbers)[two] kings of the #myhl(other)[Amorites]; it was not by your sword or by your bow. 
#versenum(13) I gave you a land on which you had not #underline[labored] and cities that you had not built, and you dwell in them. You eat the fruit of vineyards and olive orchards that you did not #underline[plant].’


  
#section-heading[Choose Whom You Will Serve]


#versenum(14) “Now therefore fear the #myhl(divine)[#smallcaps[Lord]] and serve him in #underline[sincerity] and in #underline[faithfulness]. Put away the gods that your fathers served beyond the River and in #myhl(places)[Egypt], and serve the #myhl(divine)[#smallcaps[Lord]]. 
#versenum(15) And if it is evil in your eyes to serve the #myhl(divine)[#smallcaps[Lord]], choose this day whom you will serve, whether the gods your fathers served in the region beyond the River, or the gods of the #myhl(other)[Amorites] in whose land you dwell. But as for me and my house, we will serve the #myhl(divine)[#smallcaps[Lord]].”


  
#versenum(16) Then the people answered, “Far be it from us that we should forsake the #myhl(divine)[#smallcaps[Lord]] to serve other gods, 
#versenum(17) for it is the #myhl(divine)[#smallcaps[Lord]] our #myhl(divine)[God] who brought us and our fathers up from the land of #myhl(places)[Egypt], out of the house of slavery, and who did those great #underline[signs] in our sight and #underline[preserved] us in all the way that we went, and among all the peoples through whom we passed. 
#versenum(18) And the #myhl(divine)[#smallcaps[Lord]] drove out before us all the peoples, the #myhl(other)[Amorites] who lived in the land. Therefore we also will serve the #myhl(divine)[#smallcaps[Lord]], for he is our #myhl(divine)[God].”


  
#versenum(19) But #myhl(men)[Joshua] said to the people, “You are not able to serve the #myhl(divine)[#smallcaps[Lord]], for he is a holy #myhl(divine)[God]. He is a #underline[jealous] #myhl(divine)[God]; he will not #underline[forgive] your #underline[transgressions] or your #underline[sins]. 
#versenum(20) If you forsake the #myhl(divine)[#smallcaps[Lord]] and serve foreign gods, then he will turn and do you harm and #underline[consume] you, after having done you good.” 
#versenum(21) And the people said to #myhl(men)[Joshua], “No, but we will serve the #myhl(divine)[#smallcaps[Lord]].” 
#versenum(22) Then #myhl(men)[Joshua] said to the people, “You are witnesses against yourselves that you have chosen the #myhl(divine)[#smallcaps[Lord]], to serve him.” And they said, “We are witnesses.” 
#versenum(23) He said, “Then put away the foreign gods that are among you, and #underline[incline] your heart to the #myhl(divine)[#smallcaps[Lord], the God of Israel].” 
#versenum(24) And the people said to #myhl(men)[Joshua], “The #myhl(divine)[#smallcaps[Lord]] our #myhl(divine)[God] we will serve, and his voice we will obey.” 
#versenum(25) So #myhl(men)[Joshua] made a covenant with the people that day, and put in place #underline[statutes] and #underline[rules] for them at #myhl(places)[Shechem]. 
#versenum(26) And #myhl(men)[Joshua] wrote these words in the Book of the Law of #myhl(divine)[God]. And he took a large stone and set it up there under the terebinth that was by the #underline[sanctuary] of the #myhl(divine)[#smallcaps[Lord]]. 
#versenum(27) And #myhl(men)[Joshua] said to all the people, “Behold, this stone shall be a witness against us, for it has heard all the words of the #myhl(divine)[#smallcaps[Lord]] that he spoke to us. Therefore it shall be a witness against you, lest you deal #underline[falsely] with your #myhl(divine)[God].” 
#versenum(28) So #myhl(men)[Joshua] sent the people away, every man to his inheritance.


  
#section-heading[Joshua’s Death and Burial]


#versenum(29) After these things #myhl(men)[Joshua] the son of #myhl(men)[Nun], the servant of the #myhl(divine)[#smallcaps[Lord]], died, being #myhl(numbers)[110] years old. 
#versenum(30) And they buried him in his own inheritance at #myhl(places)[Timnath-serah], which is in #myhl(places)[the hill country of Ephraim], north of the #myhl(places)[mountain of Gaash].


  
#versenum(31) #myhl(people-groups)[Israel] served the #myhl(divine)[#smallcaps[Lord]] all the days of #myhl(men)[Joshua], and all the days of the elders who outlived #myhl(men)[Joshua] and had known all the work that the #myhl(divine)[#smallcaps[Lord]] did for #myhl(people-groups)[Israel].


  
#versenum(32) As for the #underline[bones] of #myhl(men)[Joseph], which the #myhl(people-groups)[people of Israel] brought up from #myhl(places)[Egypt], they buried them at #myhl(places)[Shechem], in the #underline[piece] of land that #myhl(men)[Jacob] bought from the sons of #myhl(men)[Hamor] the father of #myhl(places)[Shechem] for a #myhl(numbers)[hundred] pieces of money.#footnote[Joshua 24:32 Hebrew #emph[for a hundred qesitah]; a unit of money of uncertain value] It became an inheritance of the descendants of #myhl(men)[Joseph].


  
#versenum(33) And #myhl(men)[Eleazar] the son of #myhl(men)[Aaron] died, and they buried him at #myhl(places)[Gibeah], the town of #myhl(men)[Phinehas] his son, which had been given him in #myhl(places)[the hill country of Ephraim].


  
#chapter-heading[Judges 1]


#section-heading[The Continuing Conquest of Canaan]


#versenum(1) After the death of #myhl(men)[Joshua], the #myhl(people-groups)[people of Israel] inquired of the #myhl(divine)[#smallcaps[Lord]], “Who shall go up #myhl(numbers)[first] for us against the #myhl(other)[Canaanites], to fight against them?” 
#versenum(2) The #myhl(divine)[#smallcaps[Lord]] said, “#myhl(other)[Judah] shall go up; behold, I have given the land into his hand.” 
#versenum(3) And #myhl(other)[Judah] said to #myhl(men)[Simeon] his brother, “Come up with me into the territory allotted to me, that we may fight against the #myhl(other)[Canaanites]. And I likewise will go with you into the territory allotted to you.” So #myhl(men)[Simeon] went with him. 
#versenum(4) Then #myhl(other)[Judah] went up and the #myhl(divine)[#smallcaps[Lord]] gave the #myhl(other)[Canaanites] and the #myhl(other)[Perizzites] into their hand, and they defeated #myhl(numbers)[10,000] of them at #myhl(places)[Bezek]. 
#versenum(5) They found #myhl(men)[Adoni-bezek] at #myhl(places)[Bezek] and fought against him and defeated the #myhl(other)[Canaanites] and the #myhl(other)[Perizzites]. 
#versenum(6) #myhl(men)[Adoni-bezek] fled, but they pursued him and caught him and cut off his thumbs and his big toes. 
#versenum(7) And #myhl(men)[Adoni-bezek] said, “#myhl(numbers)[Seventy] kings with their thumbs and their big toes cut off used to #underline[pick] up #underline[scraps] under my #underline[table]. As I have done, so #myhl(divine)[God] has #underline[repaid] me.” And they brought him to #myhl(places)[Jerusalem], and he died there.


  
#versenum(8) And the men of #myhl(other)[Judah] fought against #myhl(places)[Jerusalem] and captured it and struck it with the edge of the sword and set the city on fire. 
#versenum(9) And afterward the men of #myhl(other)[Judah] went down to fight against the #myhl(other)[Canaanites] who lived in the hill country, in the #myhl(places)[Negeb], and in the lowland. 
#versenum(10) And #myhl(other)[Judah] went against the #myhl(other)[Canaanites] who lived in #myhl(places)[Hebron] (now the name of #myhl(places)[Hebron] was formerly #myhl(places)[Kiriath-arba]), and they defeated #myhl(men)[Sheshai] and #myhl(men)[Ahiman] and #myhl(men)[Talmai].


  
#versenum(11) From there they went against the inhabitants of #myhl(places)[Debir]. The name of #myhl(places)[Debir] was formerly #myhl(places)[Kiriath-sepher]. 
#versenum(12) And #myhl(men)[Caleb] said, “He who attacks #myhl(places)[Kiriath-sepher] and captures it, I will give him #myhl(women)[Achsah] my daughter as wife.” 
#versenum(13) And #myhl(men)[Othniel] the son of #myhl(men)[Kenaz], #myhl(men)[Caleb]’s younger brother, captured it. And he gave him #myhl(women)[Achsah] his daughter as wife. 
#versenum(14) When she came to him, she urged him to ask her father for a field. And she dismounted from her donkey, and #myhl(men)[Caleb] said to her, “What do you want?” 
#versenum(15) She said to him, “Give me a blessing. Since you have given me the land of the #myhl(places)[Negeb], give me also springs of water.” And #myhl(men)[Caleb] gave her the upper springs and the lower springs.


  
#versenum(16) And the descendants of the #myhl(other)[Kenite], #myhl(men)[Moses]’ father-in-law, went up with the people of #myhl(other)[Judah] from the city of palms into the wilderness of #myhl(other)[Judah], which lies in the #myhl(places)[Negeb] near #myhl(places)[Arad], and they went and settled with the people. 
#versenum(17) And #myhl(other)[Judah] went with #myhl(men)[Simeon] his brother, and they defeated the #myhl(other)[Canaanites] who inhabited #underline[#myhl(places)[Zephath]] and devoted it to destruction. So the name of the city was called #myhl(places)[Hormah].#footnote[Judges 1:17 #emph[Hormah] means #emph[utter destruction]] 
#versenum(18) #myhl(other)[Judah] also captured #myhl(places)[Gaza] with its territory, and #myhl(places)[Ashkelon] with its territory, and #myhl(places)[Ekron] with its territory. 
#versenum(19) And the #myhl(divine)[#smallcaps[Lord]] was with #myhl(other)[Judah], and he took possession of the hill country, but he could not drive out the inhabitants of the plain because they had chariots of iron. 
#versenum(20) And #myhl(places)[Hebron] was given to #myhl(men)[Caleb], as #myhl(men)[Moses] had said. And he drove out from it the #myhl(numbers)[three] sons of #myhl(men)[Anak]. 
#versenum(21) But the people of #myhl(men)[Benjamin] did not drive out the #myhl(other)[Jebusites] who lived in #myhl(places)[Jerusalem], so the #myhl(other)[Jebusites] have lived with the people of #myhl(men)[Benjamin] in #myhl(places)[Jerusalem] to this day.


  
#versenum(22) The house of #myhl(men)[Joseph] also went up against #myhl(places)[Bethel], and the #myhl(divine)[#smallcaps[Lord]] was with them. 
#versenum(23) And the house of #myhl(men)[Joseph] #underline[scouted] out #myhl(places)[Bethel]. (Now the name of the city was formerly #myhl(places)[Luz].) 
#versenum(24) And the spies saw a man coming out of the city, and they said to him, “Please show us the way into the city, and we will deal kindly with you.” 
#versenum(25) And he #underline[showed] them the way into the city. And they struck the city with the edge of the sword, but they let the man and all his family go. 
#versenum(26) And the man went to the land of the #myhl(other)[Hittites] and built a city and called its name #myhl(places)[Luz]. That is its name to this day.


  
#section-heading[Failure to Complete the Conquest]


#versenum(27) #myhl(other)[Manasseh] did not drive out the inhabitants of #myhl(places)[Beth-shean] and its villages, or #myhl(places)[Taanach] and its villages, or the inhabitants of #myhl(places)[Dor] and its villages, or the inhabitants of #myhl(places)[Ibleam] and its villages, or the inhabitants of #myhl(places)[Megiddo] and its villages, for the #myhl(other)[Canaanites] persisted in dwelling in that land. 
#versenum(28) When #myhl(people-groups)[Israel] grew strong, they put the #myhl(other)[Canaanites] to forced labor, but did not drive them out completely.


  
#versenum(29) And #myhl(other)[Ephraim] did not drive out the #myhl(other)[Canaanites] who lived in #myhl(places)[Gezer], so the #myhl(other)[Canaanites] lived in #myhl(places)[Gezer] among them.


  
#versenum(30) #myhl(other)[Zebulun] did not drive out the inhabitants of #underline[#myhl(places)[Kitron]], or the inhabitants of #underline[#myhl(places)[Nahalol]], so the #myhl(other)[Canaanites] lived among them, but became subject to forced labor.


  
#versenum(31) #myhl(other)[Asher] did not drive out the inhabitants of #underline[#myhl(places)[Acco]], or the inhabitants of #myhl(places)[Sidon] or of #underline[#myhl(places)[Ahlab]] or of #myhl(places)[Achzib] or of #underline[#myhl(places)[Helbah]] or of #underline[#myhl(places)[Aphik]] or of #myhl(places)[Rehob], 
#versenum(32) so the #underline[#myhl(other)[Asherites]] lived among the #myhl(other)[Canaanites], the inhabitants of the land, for they did not drive them out.


  
#versenum(33) #myhl(other)[Naphtali] did not drive out the inhabitants of #myhl(places)[Beth-shemesh], or the inhabitants of #myhl(places)[Beth-anath], so they lived among the #myhl(other)[Canaanites], the inhabitants of the land. Nevertheless, the inhabitants of #myhl(places)[Beth-shemesh] and of #myhl(places)[Beth-anath] became subject to forced labor for them.


  
#versenum(34) The #myhl(other)[Amorites] pressed the people of #myhl(other)[Dan] back into the hill country, for they did not allow them to come down to the plain. 
#versenum(35) The #myhl(other)[Amorites] persisted in dwelling in #myhl(places)[Mount Heres], in #myhl(places)[Aijalon], and in #underline[#myhl(places)[Shaalbim]], but the hand of the house of #myhl(men)[Joseph] rested #underline[heavily] on them, and they became subject to forced labor. 
#versenum(36) And the border of the #myhl(other)[Amorites] ran from the ascent of #myhl(places)[Akrabbim], from #underline[#myhl(places)[Sela]] and #underline[upward].


  
#chapter-heading[Judges 2]


#section-heading[Israel’s Disobedience]


#versenum(1) Now the #myhl(divine)[angel of the #smallcaps[Lord]] went up from #myhl(places)[Gilgal] to #myhl(places)[Bochim]. And he said, “I brought you up from #myhl(places)[Egypt] and brought you into the land that I swore to give to your fathers. I said, ‘I will never break my covenant with you, 
#versenum(2) and you shall make no covenant with the inhabitants of this land; you shall break down their #underline[altars].’ But you have not obeyed my voice. What is this you have done? 
#versenum(3) So now I say, I will not drive them out before you, but they shall become thorns in your sides, and their gods shall be a snare to you.” 
#versenum(4) As soon as the #myhl(divine)[angel of the #smallcaps[Lord]] spoke these words to all the #myhl(people-groups)[people of Israel], the people lifted up their voices and wept. 
#versenum(5) And they called the name of that place #myhl(places)[Bochim].#footnote[Judges 2:5 #emph[Bochim] means #emph[weepers]] And they sacrificed there to the #myhl(divine)[#smallcaps[Lord]].


  
#section-heading[The Death of Joshua]


#versenum(6) When #myhl(men)[Joshua] #underline[dismissed] the people, the #myhl(people-groups)[people of Israel] went each to his inheritance to take possession of the land. 
#versenum(7) And the people served the #myhl(divine)[#smallcaps[Lord]] all the days of #myhl(men)[Joshua], and all the days of the elders who outlived #myhl(men)[Joshua], who had seen all the great work that the #myhl(divine)[#smallcaps[Lord]] had done for #myhl(people-groups)[Israel]. 
#versenum(8) And #myhl(men)[Joshua] the son of #myhl(men)[Nun], the servant of the #myhl(divine)[#smallcaps[Lord]], died at the age of #myhl(numbers)[110] years. 
#versenum(9) And they buried him within the #underline[boundaries] of his inheritance in #underline[#myhl(places)[Timnath-heres]], in #myhl(places)[the hill country of Ephraim], north of the #myhl(places)[mountain of Gaash]. 
#versenum(10) And all that generation also were gathered to their fathers. And there arose another generation after them who did not know the #myhl(divine)[#smallcaps[Lord]] or the work that he had done for #myhl(people-groups)[Israel].


  
#section-heading[Israel’s Unfaithfulness]


#versenum(11) And the #myhl(people-groups)[people of Israel] did what was evil in the sight of the #myhl(divine)[#smallcaps[Lord]] and served the #myhl(other)[Baals]. 
#versenum(12) And they abandoned the #myhl(divine)[#smallcaps[Lord], the God] of their fathers, who had brought them out of the land of #myhl(places)[Egypt]. They went after other gods, from among the gods of the peoples who were around them, and bowed down to them. And they #underline[provoked] the #myhl(divine)[#smallcaps[Lord]] to anger. 
#versenum(13) They abandoned the #myhl(divine)[#smallcaps[Lord]] and served the #myhl(other)[Baals] and the #myhl(other)[Ashtaroth]. 
#versenum(14) So the anger of the #myhl(divine)[#smallcaps[Lord]] was kindled against #myhl(people-groups)[Israel], and he gave them over to #underline[plunderers], who plundered them. And he sold them into the hand of their surrounding enemies, so that they could no longer #underline[withstand] their enemies. 
#versenum(15) Whenever they marched out, the hand of the #myhl(divine)[#smallcaps[Lord]] was against them for harm, as the #myhl(divine)[#smallcaps[Lord]] had #underline[warned], and as the #myhl(divine)[#smallcaps[Lord]] had sworn to them. And they were in #underline[terrible] distress.


  
#section-heading[The LORD Raises Up Judges]


#versenum(16) Then the #myhl(divine)[#smallcaps[Lord]] raised up judges, who saved them out of the hand of those who plundered them. 
#versenum(17) Yet they did not listen to their judges, for they whored after other gods and bowed down to them. They soon turned aside from the way in which their fathers had walked, who had obeyed the commandments of the #myhl(divine)[#smallcaps[Lord]], and they did not do so. 
#versenum(18) Whenever the #myhl(divine)[#smallcaps[Lord]] raised up judges for them, the #myhl(divine)[#smallcaps[Lord]] was with the judge, and he saved them from the hand of their enemies all the days of the judge. For the #myhl(divine)[#smallcaps[Lord]] was moved to #underline[pity] by their #underline[groaning] because of those who #underline[afflicted] and oppressed them. 
#versenum(19) But whenever the judge died, they turned back and were more #underline[corrupt] than their fathers, going after other gods, #underline[serving] them and bowing down to them. They did not #underline[drop] any of their #underline[practices] or their #underline[stubborn] ways. 
#versenum(20) So the anger of the #myhl(divine)[#smallcaps[Lord]] was kindled against #myhl(people-groups)[Israel], and he said, “Because this people have transgressed my covenant that I commanded their fathers and have not obeyed my voice, 
#versenum(21) I will no longer drive out before them any of the nations that #myhl(men)[Joshua] left when he died, 
#versenum(22) in order to test #myhl(people-groups)[Israel] by them, whether they will take care to walk in the way of the #myhl(divine)[#smallcaps[Lord]] as their fathers did, or not.” 
#versenum(23) So the #myhl(divine)[#smallcaps[Lord]] left those nations, not #underline[driving] them out quickly, and he did not give them into the hand of #myhl(men)[Joshua].


  
#chapter-heading[Judges 3]


#versenum(1) Now these are the nations that the #myhl(divine)[#smallcaps[Lord]] left, to test #myhl(people-groups)[Israel] by them, that is, all in #myhl(places)[Israel] who had not #underline[experienced] all the #underline[wars] in #myhl(places)[Canaan]. 
#versenum(2) It was only in order that the generations of the #myhl(people-groups)[people of Israel] might know war, to teach war to those who had not known it before. 
#versenum(3) These are the nations: the #myhl(numbers)[five] lords of the #myhl(other)[Philistines] and all the #myhl(other)[Canaanites] and the #myhl(other)[Sidonians] and the #myhl(other)[Hivites] who lived on #myhl(places)[Mount Lebanon], from Mount #underline[#myhl(places)[Baal-hermon]] as far as #myhl(places)[Lebo-hamath]. 
#versenum(4) They were for the #underline[testing] of #myhl(people-groups)[Israel], to know whether #myhl(people-groups)[Israel] would obey the commandments of the #myhl(divine)[#smallcaps[Lord]], which he commanded their fathers by the hand of #myhl(men)[Moses]. 
#versenum(5) So the #myhl(people-groups)[people of Israel] lived among the #myhl(other)[Canaanites], the #myhl(other)[Hittites], the #myhl(other)[Amorites], the #myhl(other)[Perizzites], the #myhl(other)[Hivites], and the #myhl(other)[Jebusites]. 
#versenum(6) And their daughters they took to themselves for wives, and their own daughters they gave to their sons, and they served their gods.


  
#section-heading[Othniel]


#versenum(7) And the #myhl(people-groups)[people of Israel] did what was evil in the sight of the #myhl(divine)[#smallcaps[Lord]]. They #underline[forgot] the #myhl(divine)[#smallcaps[Lord]] their #myhl(divine)[God] and served the #myhl(other)[Baals] and the #underline[#myhl(other)[Asheroth]]. 
#versenum(8) Therefore the anger of the #myhl(divine)[#smallcaps[Lord]] was kindled against #myhl(people-groups)[Israel], and he sold them into the hand of #myhl(men)[Cushan-rishathaim] king of #myhl(places)[Mesopotamia]. And the #myhl(people-groups)[people of Israel] served #myhl(men)[Cushan-rishathaim] #myhl(numbers)[eight] years. 
#versenum(9) But when the #myhl(people-groups)[people of Israel] cried out to the #myhl(divine)[#smallcaps[Lord]], the #myhl(divine)[#smallcaps[Lord]] raised up a deliverer for the #myhl(people-groups)[people of Israel], who saved them, #myhl(men)[Othniel] the son of #myhl(men)[Kenaz], #myhl(men)[Caleb]’s younger brother. 
#versenum(10) The #myhl(divine)[Spirit] of the #myhl(divine)[#smallcaps[Lord]] was upon him, and he judged #myhl(people-groups)[Israel]. He went out to war, and the #myhl(divine)[#smallcaps[Lord]] gave #myhl(men)[Cushan-rishathaim] king of #myhl(places)[Mesopotamia] into his hand. And his hand #underline[prevailed] over #myhl(men)[Cushan-rishathaim]. 
#versenum(11) So the land had rest for #myhl(numbers)[forty] years. Then #myhl(men)[Othniel] the son of #myhl(men)[Kenaz] died.


  
#section-heading[Ehud]


#versenum(12) And the #myhl(people-groups)[people of Israel] again did what was evil in the sight of the #myhl(divine)[#smallcaps[Lord]], and the #myhl(divine)[#smallcaps[Lord]] strengthened #myhl(men)[Eglon] the king of #myhl(places)[Moab] against #myhl(people-groups)[Israel], because they had done what was evil in the sight of the #myhl(divine)[#smallcaps[Lord]]. 
#versenum(13) He gathered to himself the #myhl(other)[Ammonites] and the #myhl(other)[Amalekites], and went and defeated #myhl(people-groups)[Israel]. And they took possession of the city of palms. 
#versenum(14) And the #myhl(people-groups)[people of Israel] served #myhl(men)[Eglon] the king of #myhl(places)[Moab] #myhl(numbers)[eighteen] years.


  
#versenum(15) Then the #myhl(people-groups)[people of Israel] cried out to the #myhl(divine)[#smallcaps[Lord]], and the #myhl(divine)[#smallcaps[Lord]] raised up for them a deliverer, #myhl(men)[Ehud], the son of #underline[#myhl(men)[Gera]], the #underline[#myhl(other)[Benjaminite]], a left-handed man. The #myhl(people-groups)[people of Israel] sent tribute by him to #myhl(men)[Eglon] the king of #myhl(places)[Moab]. 
#versenum(16) And #myhl(men)[Ehud] made for himself a sword with #myhl(numbers)[two] #underline[edges], a #underline[cubit]#footnote[Judges 3:16 A #emph[cubit] was about 18 inches or 45 centimeters] in length, and he bound it on his right thigh under his clothes. 
#versenum(17) And he presented the tribute to #myhl(men)[Eglon] king of #myhl(places)[Moab]. Now #myhl(men)[Eglon] was a very fat man. 
#versenum(18) And when #myhl(men)[Ehud] had finished #underline[presenting] the tribute, he sent away the people who carried the tribute. 
#versenum(19) But he himself turned back at the idols near #myhl(places)[Gilgal] and said, “I have a secret message for you, O king.” And he commanded, “#underline[Silence].” And all his #underline[attendants] went out from his presence. 
#versenum(20) And #myhl(men)[Ehud] came to him as he was sitting alone in his cool roof chamber. And #myhl(men)[Ehud] said, “I have a message from #myhl(divine)[God] for you.” And he arose from his #underline[seat]. 
#versenum(21) And #myhl(men)[Ehud] reached with his left hand, took the sword from his right thigh, and thrust it into his belly. 
#versenum(22) And the #underline[hilt] also went in after the blade, and the fat closed over the blade, for he did not pull the sword out of his belly; and the #underline[dung] came out. 
#versenum(23) Then #myhl(men)[Ehud] went out into the #underline[porch]#footnote[Judges 3:23 The meaning of the Hebrew word is uncertain] and closed the doors of the roof chamber behind him and locked them.


  
#versenum(24) When he had gone, the servants came, and when they saw that the doors of the roof chamber were locked, they thought, “Surely he is #underline[relieving] himself in the #underline[closet] of the cool chamber.” 
#versenum(25) And they #underline[waited] till they were #underline[embarrassed]. But when he still did not open the doors of the roof chamber, they took the #underline[key] and opened them, and there lay their lord dead on the floor.


  
#versenum(26) #myhl(men)[Ehud] escaped while they #underline[delayed], and he passed beyond the idols and escaped to #underline[#myhl(places)[Seirah]]. 
#versenum(27) When he arrived, he sounded the trumpet in #myhl(places)[the hill country of Ephraim]. Then the #myhl(people-groups)[people of Israel] went down with him from the hill country, and he was their leader. 
#versenum(28) And he said to them, “Follow after me, for the #myhl(divine)[#smallcaps[Lord]] has given your enemies the #myhl(other)[Moabites] into your hand.” So they went down after him and seized the fords of the #myhl(places)[Jordan] against the #myhl(other)[Moabites] and did not allow anyone to pass over. 
#versenum(29) And they killed at that time about #myhl(numbers)[10,000] of the #myhl(other)[Moabites], all strong, #underline[able-bodied] men; not a man escaped. 
#versenum(30) So #myhl(places)[Moab] was subdued that day under the hand of #myhl(people-groups)[Israel]. And the land had rest for #underline[#myhl(numbers)[eighty]] years.


  
#section-heading[Shamgar]


#versenum(31) After him was #myhl(men)[Shamgar] the son of #myhl(men)[Anath], who killed #myhl(numbers)[600] of the #myhl(other)[Philistines] with an #underline[oxgoad], and he also saved #myhl(people-groups)[Israel].


  
#chapter-heading[Judges 4]


#section-heading[Deborah and Barak]


#versenum(1) And the #myhl(people-groups)[people of Israel] again did what was evil in the sight of the #myhl(divine)[#smallcaps[Lord]] after #myhl(men)[Ehud] died. 
#versenum(2) And the #myhl(divine)[#smallcaps[Lord]] sold them into the hand of #myhl(men)[Jabin] king of #myhl(places)[Canaan], who reigned in #myhl(places)[Hazor]. The commander of his army was #myhl(men)[Sisera], who lived in #myhl(places)[Harosheth-hagoyim]. 
#versenum(3) Then the #myhl(people-groups)[people of Israel] cried out to the #myhl(divine)[#smallcaps[Lord]] for help, for he had #myhl(numbers)[900] chariots of iron and he oppressed the #myhl(people-groups)[people of Israel] #underline[cruelly] for #myhl(numbers)[twenty] years.


  
#versenum(4) Now #myhl(women)[Deborah], a #underline[prophetess], the wife of #underline[#myhl(men)[Lappidoth]], was #underline[judging] #myhl(people-groups)[Israel] at that time. 
#versenum(5) She used to sit under the #underline[palm] of #myhl(women)[Deborah] between #myhl(places)[Ramah] and #myhl(places)[Bethel] in #myhl(places)[the hill country of Ephraim], and the #myhl(people-groups)[people of Israel] came up to her for judgment. 
#versenum(6) She sent and summoned #myhl(men)[Barak] the son of #myhl(men)[Abinoam] from #underline[#myhl(places)[Kedesh-naphtali]] and said to him, “Has not the #myhl(divine)[#smallcaps[Lord], the God of Israel], commanded you, ‘Go, gather your men at #myhl(places)[Mount Tabor], taking #myhl(numbers)[10,000] from the people of #myhl(other)[Naphtali] and the people of #myhl(other)[Zebulun]. 
#versenum(7) And I will draw out #myhl(men)[Sisera], the #underline[general] of #underline[#myhl(men)[Jabin]’s] army, to meet you by the river #myhl(places)[Kishon] with his chariots and his troops, and I will give him into your hand’?” 
#versenum(8) #myhl(men)[Barak] said to her, “If you will go with me, I will go, but if you will not go with me, I will not go.” 
#versenum(9) And she said, “I will surely go with you. Nevertheless, the #underline[road] on which you are going will not lead to your glory, for the #myhl(divine)[#smallcaps[Lord]] will #underline[sell] #myhl(men)[Sisera] into the hand of a woman.” Then #myhl(women)[Deborah] arose and went with #myhl(men)[Barak] to #myhl(places)[Kedesh]. 
#versenum(10) And #myhl(men)[Barak] called out #myhl(other)[Zebulun] and #myhl(other)[Naphtali] to #myhl(places)[Kedesh]. And #myhl(numbers)[10,000] men went up at his heels, and #myhl(women)[Deborah] went up with him.


  
#versenum(11) Now #myhl(men)[Heber] the #myhl(other)[Kenite] had #underline[separated] from the #underline[#myhl(other)[Kenites]], the descendants of #underline[#myhl(men)[Hobab]] the father-in-law of #myhl(men)[Moses], and had #underline[pitched] his tent as far away as the oak in #myhl(places)[Zaanannim], which is near #myhl(places)[Kedesh].


  
#versenum(12) When #myhl(men)[Sisera] was told that #myhl(men)[Barak] the son of #myhl(men)[Abinoam] had gone up to #myhl(places)[Mount Tabor], 
#versenum(13) #myhl(men)[Sisera] called out all his chariots, #myhl(numbers)[900] chariots of iron, and all the men who were with him, from #myhl(places)[Harosheth-hagoyim] to the river #myhl(places)[Kishon]. 
#versenum(14) And #myhl(women)[Deborah] said to #myhl(men)[Barak], “Up! For this is the day in which the #myhl(divine)[#smallcaps[Lord]] has given #myhl(men)[Sisera] into your hand. Does not the #myhl(divine)[#smallcaps[Lord]] go out before you?” So #myhl(men)[Barak] went down from #myhl(places)[Mount Tabor] with #myhl(numbers)[10,000] men following him. 
#versenum(15) And the #myhl(divine)[#smallcaps[Lord]] routed #myhl(men)[Sisera] and all his chariots and all his army before #myhl(men)[Barak] by the edge of the sword. And #myhl(men)[Sisera] got down from his chariot and fled away on foot. 
#versenum(16) And #myhl(men)[Barak] pursued the chariots and the army to #myhl(places)[Harosheth-hagoyim], and all the army of #myhl(men)[Sisera] fell by the edge of the sword; not a man was left.


  
#versenum(17) But #myhl(men)[Sisera] fled away on foot to the tent of #myhl(women)[Jael], the wife of #myhl(men)[Heber] the #myhl(other)[Kenite], for there was peace between #myhl(men)[Jabin] the king of #myhl(places)[Hazor] and the house of #myhl(men)[Heber] the #myhl(other)[Kenite]. 
#versenum(18) And #myhl(women)[Jael] came out to meet #myhl(men)[Sisera] and said to him, “Turn aside, my lord; turn aside to me; do not be afraid.” So he turned aside to her into the tent, and she covered him with a #underline[rug]. 
#versenum(19) And he said to her, “Please give me a little water to drink, for I am thirsty.” So she opened a #underline[skin] of milk and gave him a drink and covered him. 
#versenum(20) And he said to her, “Stand at the #underline[opening] of the tent, and if any man comes and #underline[asks] you, ‘Is anyone here?’ say, ‘No.’” 
#versenum(21) But #myhl(women)[Jael] the wife of #myhl(men)[Heber] took a tent peg, and took a #underline[hammer] in her hand. Then she went softly to him and drove the peg into his temple until it went down into the ground while he was lying #underline[fast] #underline[asleep] from #underline[weariness]. So he died. 
#versenum(22) And behold, as #myhl(men)[Barak] was pursuing #myhl(men)[Sisera], #myhl(women)[Jael] went out to meet him and said to him, “Come, and I will show you the man whom you are seeking.” So he went in to her tent, and there lay #myhl(men)[Sisera] dead, with the tent peg in his temple.


  
#versenum(23) So on that day #myhl(divine)[God] subdued #myhl(men)[Jabin] the king of #myhl(places)[Canaan] before the #myhl(people-groups)[people of Israel]. 
#versenum(24) And the hand of the #myhl(people-groups)[people of Israel] pressed harder and harder against #myhl(men)[Jabin] the king of #myhl(places)[Canaan], until they destroyed #myhl(men)[Jabin] king of #myhl(places)[Canaan].


  
#chapter-heading[Judges 5]


#section-heading[The Song of Deborah and Barak]


#versenum(1) Then #underline[sang] #myhl(women)[Deborah] and #myhl(men)[Barak] the son of #myhl(men)[Abinoam] on that day:


    
#versenum(2) “That the leaders took the lead in #myhl(places)[Israel],\
    #vin that the people offered themselves willingly,\
    #vin bless the #myhl(divine)[#smallcaps[Lord]]!\


    
#versenum(3) “Hear, O kings; give #underline[ear], O princes;\
    #vin to the #myhl(divine)[#smallcaps[Lord]] I will #underline[sing];\
    #vin I will make #underline[melody] to the #myhl(divine)[#smallcaps[Lord], the God of Israel].\


    
#versenum(4) “#myhl(divine)[#smallcaps[Lord]], when you went out from #myhl(places)[Seir],\
    #vin when you marched from the region of #myhl(places)[Edom],\
    the earth #underline[trembled]\
    #vin and the heavens dropped,\
    #vin #underline[yes], the #underline[clouds] dropped water.\
    
#versenum(5) The mountains #underline[quaked] before the #myhl(divine)[#smallcaps[Lord]],\
    #vin even #underline[#myhl(places)[Sinai]] before the #myhl(divine)[#smallcaps[Lord],#footnote[Judges 5:5 Or #emph[before the Lord, the One of Sinai, before the Lord]] the God of Israel].\


    
#versenum(6) “In the days of #myhl(men)[Shamgar], son of #myhl(men)[Anath],\
    #vin in the days of #myhl(women)[Jael], the highways were abandoned,\
    #vin and #underline[travelers] kept to the #underline[byways].\
    
#versenum(7) The villagers ceased in #myhl(places)[Israel];\
    #vin they ceased to be until I arose;\
    #vin I, #myhl(women)[Deborah], arose as a mother in #myhl(places)[Israel].\
    
#versenum(8) When new gods were chosen,\
    #vin then war was in the gates.\
    Was #underline[shield] or #underline[spear] to be seen\
    #vin among #myhl(numbers)[forty thousand] in #myhl(places)[Israel]?\
    
#versenum(9) My heart goes out to the commanders of #myhl(people-groups)[Israel]\
    #vin who offered themselves willingly among the people.\
    #vin Bless the #myhl(divine)[#smallcaps[Lord]].\


    
#versenum(10) “Tell of it, you who #underline[ride] on #underline[white] donkeys,\
    #vin you who sit on rich #underline[carpets]#footnote[Judges 5:10 The meaning of the Hebrew word is uncertain; it may connote #emph[saddle blankets]]\
    #vin and you who walk by the way.\
    
#versenum(11) To the sound of #underline[musicians]#footnote[Judges 5:11 Or #emph[archers]; the meaning of the Hebrew word is uncertain] at the #underline[watering] places,\
    #vin there they #underline[repeat] the righteous triumphs of the #myhl(divine)[#smallcaps[Lord]],\
    #vin the righteous triumphs of his villagers in #myhl(places)[Israel].\


    “Then down to the gates marched the people of the #myhl(divine)[#smallcaps[Lord]].\


    
#versenum(12) “Awake, awake, #myhl(women)[Deborah]!\
    #vin Awake, awake, break out in a #underline[song]!\
    Arise, #myhl(men)[Barak], lead away your #underline[captives],\
    #vin O son of #myhl(men)[Abinoam].\
    
#versenum(13) Then down marched the remnant of the #underline[noble];\
    #vin the people of the #myhl(divine)[#smallcaps[Lord]] marched down for me against the mighty.\
    
#versenum(14) From #myhl(other)[Ephraim] their #underline[root] they marched down into the valley,#footnote[Judges 5:14 Septuagint; Hebrew #emph[in Amalek]]\
    #vin following you, #myhl(men)[Benjamin], with your #underline[kinsmen];\
    from #myhl(men)[Machir] marched down the commanders,\
    #vin and from #myhl(other)[Zebulun] those who bear the #underline[lieutenant’s]#footnote[Judges 5:14 Hebrew #emph[commander’s]] staff;\
    
#versenum(15) the princes of #myhl(other)[Issachar] came with #myhl(women)[Deborah],\
    #vin and #myhl(other)[Issachar] #underline[faithful] to #myhl(men)[Barak];\
    #vin into the valley they rushed at his heels.\
    Among the clans of #myhl(other)[Reuben]\
    #vin there were great searchings of heart.\
    
#versenum(16) Why did you sit still among the #underline[sheepfolds],\
    #vin to hear the #underline[whistling] for the #underline[flocks]?\
    Among the clans of #myhl(other)[Reuben]\
    #vin there were great searchings of heart.\
    
#versenum(17) #myhl(places)[Gilead] stayed beyond the #myhl(places)[Jordan];\
    #vin and #myhl(other)[Dan], why did he stay with the #underline[ships]?\
    #myhl(other)[Asher] sat still at the coast of the sea,\
    #vin #underline[staying] by his #underline[landings].\
    
#versenum(18) #myhl(other)[Zebulun] is a people who risked their lives to the death;\
    #vin #myhl(other)[Naphtali], too, on the #underline[heights] of the field.\


    
#versenum(19) “The kings came, they fought;\
    #vin then fought the kings of #myhl(places)[Canaan],\
    at #myhl(places)[Taanach], by the waters of #myhl(places)[Megiddo];\
    #vin they got no #underline[spoils] of silver.\
    
#versenum(20) From heaven the #underline[stars] fought,\
    #vin from their #underline[courses] they fought against #myhl(men)[Sisera].\
    
#versenum(21) The torrent #myhl(places)[Kishon] #underline[swept] them away,\
    #vin the #underline[ancient] torrent, the torrent #myhl(places)[Kishon].\
    #vin March on, my soul, with might!\


    
#versenum(22) “Then #underline[loud] beat the horses’ #underline[hoofs]\
    #vin with the galloping, galloping of his #underline[steeds].\


    
#versenum(23) “Curse #underline[#myhl(places)[Meroz]], says the #myhl(divine)[angel of the #smallcaps[Lord]],\
    #vin curse its inhabitants #underline[thoroughly],\
    because they did not come to the help of the #myhl(divine)[#smallcaps[Lord]],\
    #vin to the help of the #myhl(divine)[#smallcaps[Lord]] against the mighty.\


    
#versenum(24) “Most blessed of women be #myhl(women)[Jael],\
    #vin the wife of #myhl(men)[Heber] the #myhl(other)[Kenite],\
    #vin of #underline[tent-dwelling] women most blessed.\
    
#versenum(25) He asked for water and she gave him milk;\
    #vin she brought him #underline[curds] in a #underline[noble’s] bowl.\
    
#versenum(26) She sent her hand to the tent peg\
    #vin and her right hand to the #underline[workmen’s] #underline[mallet];\
    she struck #myhl(men)[Sisera];\
    #vin she crushed his head;\
    #vin she #underline[shattered] and #underline[pierced] his temple.\
    
#versenum(27) Between her feet\
    #vin he sank, he fell, he lay still;\
    between her feet\
    #vin he sank, he fell;\
    where he sank,\
    #vin there he fell—dead.\


    
#versenum(28) “Out of the window she #underline[peered],\
    #vin the mother of #myhl(men)[Sisera] #underline[wailed] through the #underline[lattice]:\
    ‘Why is his chariot so long in coming?\
    #vin Why #underline[tarry] the #underline[hoofbeats] of his chariots?’\
    
#versenum(29) Her #underline[wisest] #underline[princesses] answer,\
    #vin indeed, she #underline[answers] #underline[herself],\
    
#versenum(30) ‘Have they not found and divided the spoil?—\
    #vin A womb or #myhl(numbers)[two] for every man;\
    spoil of dyed materials for #myhl(men)[Sisera],\
    #vin spoil of dyed materials embroidered,\
    #vin #myhl(numbers)[two] pieces of dyed work embroidered for the #underline[neck] as spoil?’\


    
#versenum(31) “So may all your enemies perish, O #myhl(divine)[#smallcaps[Lord]]!\
    #vin But your #underline[friends] be like the sun as he rises in his might.”\


      And the land had rest for #myhl(numbers)[forty] years.


  
#chapter-heading[Judges 6]


#section-heading[Midian Oppresses Israel]


#versenum(1) The #myhl(people-groups)[people of Israel] did what was evil in the sight of the #myhl(divine)[#smallcaps[Lord]], and the #myhl(divine)[#smallcaps[Lord]] gave them into the hand of #myhl(places)[Midian] #myhl(numbers)[seven] years. 
#versenum(2) And the hand of #myhl(places)[Midian] #underline[overpowered] #myhl(people-groups)[Israel], and because of #myhl(places)[Midian] the #myhl(people-groups)[people of Israel] made for themselves the #underline[dens] that are in the mountains and the #underline[caves] and the #underline[strongholds]. 
#versenum(3) For whenever the #myhl(people-groups)[Israelites] #underline[planted] #underline[crops], the #myhl(other)[Midianites] and the #myhl(other)[Amalekites] and the people of the East would come up against them. 
#versenum(4) They would #underline[encamp] against them and devour the produce of the land, as far as #myhl(places)[Gaza], and leave no #underline[sustenance] in #myhl(places)[Israel] and no sheep or #underline[ox] or donkey. 
#versenum(5) For they would come up with their livestock and their tents; they would come like locusts in number—both they and their camels could not be counted—so that they laid #underline[waste] the land as they came in. 
#versenum(6) And #myhl(people-groups)[Israel] was brought very low because of #myhl(places)[Midian]. And the #myhl(people-groups)[people of Israel] cried out for help to the #myhl(divine)[#smallcaps[Lord]].


  
#versenum(7) When the #myhl(people-groups)[people of Israel] cried out to the #myhl(divine)[#smallcaps[Lord]] on account of the #myhl(other)[Midianites], 
#versenum(8) the #myhl(divine)[#smallcaps[Lord]] sent a #underline[prophet] to the #myhl(people-groups)[people of Israel]. And he said to them, “Thus says the #myhl(divine)[#smallcaps[Lord], the God of Israel]: I led you up from #myhl(places)[Egypt] and brought you out of the house of slavery. 
#versenum(9) And I delivered you from the hand of the #myhl(people-groups)[Egyptians] and from the hand of all who oppressed you, and drove them out before you and gave you their land. 
#versenum(10) And I said to you, ‘I am the #myhl(divine)[#smallcaps[Lord] your God]; you shall not fear the gods of the #myhl(other)[Amorites] in whose land you dwell.’ But you have not obeyed my voice.”


  
#section-heading[The Call of Gideon]


#versenum(11) Now the #myhl(divine)[angel of the #smallcaps[Lord]] came and sat under the terebinth at #myhl(places)[Ophrah], which belonged to #myhl(men)[Joash] the #underline[#myhl(other)[Abiezrite]], while his son #myhl(men)[Gideon] was beating out wheat in the winepress to hide it from the #myhl(other)[Midianites]. 
#versenum(12) And the #myhl(divine)[angel of the #smallcaps[Lord]] appeared to him and said to him, “The #myhl(divine)[#smallcaps[Lord]] is with you, O mighty man of valor.” 
#versenum(13) And #myhl(men)[Gideon] said to him, “Please, my lord, if the #myhl(divine)[#smallcaps[Lord]] is with us, why then has all this happened to us? And where are all his wonderful deeds that our fathers #underline[recounted] to us, saying, ‘Did not the #myhl(divine)[#smallcaps[Lord]] bring us up from #myhl(places)[Egypt]?’ But now the #myhl(divine)[#smallcaps[Lord]] has forsaken us and given us into the hand of #myhl(places)[Midian].” 
#versenum(14) And the #myhl(divine)[#smallcaps[Lord]]#footnote[Judges 6:14 Septuagint #emph[the angel of the Lord]; also verse 16] turned to him and said, “Go in this might of yours and save #myhl(people-groups)[Israel] from the hand of #myhl(places)[Midian]; do not I send you?” 
#versenum(15) And he said to him, “Please, #myhl(divine)[Lord], how can I save #myhl(people-groups)[Israel]? Behold, my clan is the #underline[weakest] in #myhl(places)[Manasseh], and I am the #underline[least] in my father’s house.” 
#versenum(16) And the #myhl(divine)[#smallcaps[Lord]] said to him, “But I will be with you, and you shall strike the #myhl(other)[Midianites] as one man.” 
#versenum(17) And he said to him, “If now I have found favor in your eyes, then show me a sign that it is you who speak with me. 
#versenum(18) Please do not depart from here until I come to you and bring out my #underline[present] and set it before you.” And he said, “I will stay till you return.”


  
#versenum(19) So #myhl(men)[Gideon] went into his house and prepared a young goat and unleavened cakes from an ephah#footnote[Judges 6:19 An #emph[ephah] was about 3/5 bushel or 22 liters] of #underline[flour]. The meat he put in a #underline[basket], and the broth he put in a #underline[pot], and brought them to him under the terebinth and presented them. 
#versenum(20) And the angel of #myhl(divine)[God] said to him, “Take the meat and the unleavened cakes, and put them on this rock, and #underline[pour] the broth over them.” And he did so. 
#versenum(21) Then the #myhl(divine)[angel of the #smallcaps[Lord]] reached out the #underline[tip] of the staff that was in his hand and #underline[touched] the meat and the unleavened cakes. And fire #underline[sprang] up from the rock and #underline[consumed] the meat and the unleavened cakes. And the #myhl(divine)[angel of the #smallcaps[Lord]] #underline[vanished] from his sight. 
#versenum(22) Then #myhl(men)[Gideon] #underline[perceived] that he was the #myhl(divine)[angel of the #smallcaps[Lord]]. And #myhl(men)[Gideon] said, “Alas, O #myhl(divine)[Lord] #myhl(divine)[GOD]! For now I have seen the #myhl(divine)[angel of the #smallcaps[Lord]] face to face.” 
#versenum(23) But the #myhl(divine)[#smallcaps[Lord]] said to him, “Peace be to you. Do not fear; you shall not die.” 
#versenum(24) Then #myhl(men)[Gideon] built an altar there to the #myhl(divine)[#smallcaps[Lord]] and called it, The #myhl(divine)[#smallcaps[Lord]] Is Peace. To this day it still stands at #myhl(places)[Ophrah], which belongs to the #myhl(other)[Abiezrites].


  
#versenum(25) That night the #myhl(divine)[#smallcaps[Lord]] said to him, “Take your father’s bull, and the #myhl(numbers)[second] bull #myhl(numbers)[seven] years old, and pull down the altar of #myhl(other)[Baal] that your father has, and cut down the #myhl(other)[Asherah] that is beside it 
#versenum(26) and build an altar to the #myhl(divine)[#smallcaps[Lord] your God] on the top of the stronghold here, with stones laid in #underline[due] order. Then take the #myhl(numbers)[second] bull and offer it as a burnt offering with the wood of the #myhl(other)[Asherah] that you shall cut down.” 
#versenum(27) So #myhl(men)[Gideon] took #myhl(numbers)[ten] men of his servants and did as the #myhl(divine)[#smallcaps[Lord]] had told him. But because he was too afraid of his family and the men of the town to do it by day, he did it by night.


  
#section-heading[Gideon Destroys the Altar of Baal]


#versenum(28) When the men of the town rose early in the morning, behold, the altar of #myhl(other)[Baal] was broken down, and the #myhl(other)[Asherah] beside it was cut down, and the #myhl(numbers)[second] bull was offered on the altar that had been built. 
#versenum(29) And they said to one another, “Who has done this thing?” And after they had searched and inquired, they said, “#myhl(men)[Gideon] the son of #myhl(men)[Joash] has done this thing.” 
#versenum(30) Then the men of the town said to #myhl(men)[Joash], “Bring out your son, that he may die, for he has broken down the altar of #myhl(other)[Baal] and cut down the #myhl(other)[Asherah] beside it.” 
#versenum(31) But #myhl(men)[Joash] said to all who stood against him, “Will you contend for #myhl(other)[Baal]? Or will you save him? Whoever #underline[contends] for him shall be put to death by morning. If he is a god, let him contend for himself, because his altar has been broken down.” 
#versenum(32) Therefore on that day #myhl(men)[Gideon]#footnote[Judges 6:32 Hebrew #emph[he]] was called #myhl(men)[Jerubbaal], that is to say, “Let #myhl(other)[Baal] contend against him,” because he broke down his altar.


  
#versenum(33) Now all the #myhl(other)[Midianites] and the #myhl(other)[Amalekites] and the people of the East came together, and they crossed the #myhl(places)[Jordan] and encamped in the #myhl(places)[Valley of Jezreel]. 
#versenum(34) But the #myhl(divine)[Spirit] of the #myhl(divine)[#smallcaps[Lord]] #underline[clothed] #myhl(men)[Gideon], and he sounded the trumpet, and the #myhl(other)[Abiezrites] were called out to follow him. 
#versenum(35) And he sent messengers throughout all #myhl(other)[Manasseh], and they too were called out to follow him. And he sent messengers to #myhl(other)[Asher], #myhl(other)[Zebulun], and #myhl(other)[Naphtali], and they went up to meet them.


  
#section-heading[The Sign of the Fleece]


#versenum(36) Then #myhl(men)[Gideon] said to #myhl(divine)[God], “If you will save #myhl(people-groups)[Israel] by my hand, as you have said, 
#versenum(37) behold, I am #underline[laying] a fleece of #underline[wool] on the threshing floor. If there is dew on the fleece alone, and it is dry on all the ground, then I shall know that you will save #myhl(people-groups)[Israel] by my hand, as you have said.” 
#versenum(38) And it was so. When he rose early next morning and #underline[squeezed] the fleece, he #underline[wrung] enough dew from the fleece to #underline[fill] a bowl with water. 
#versenum(39) Then #myhl(men)[Gideon] said to #myhl(divine)[God], “Let not your anger burn against me; let me speak just once more. Please let me test just once more with the fleece. Please let it be dry on the fleece only, and on all the ground let there be dew.” 
#versenum(40) And #myhl(divine)[God] did so that night; and it was dry on the fleece only, and on all the ground there was dew.


  
#chapter-heading[Judges 7]


#section-heading[Gideon’s Three Hundred Men]


#versenum(1) Then #myhl(men)[Jerubbaal] (that is, #myhl(men)[Gideon]) and all the people who were with him rose early and encamped beside the spring of #underline[#myhl(places)[Harod]]. And #myhl(places)[the camp of Midian] was north of them, by the hill of #underline[#myhl(places)[Moreh]], in the valley.


  
#versenum(2) The #myhl(divine)[#smallcaps[Lord]] said to #myhl(men)[Gideon], “The people with you are too many for me to give the #myhl(other)[Midianites] into their hand, lest #myhl(people-groups)[Israel] #underline[boast] over me, saying, ‘My own hand has saved me.’ 
#versenum(3) Now therefore #underline[proclaim] in the ears of the people, saying, ‘Whoever is #underline[fearful] and #underline[trembling], let him return home and hurry away from #myhl(places)[Mount Gilead].’” Then #myhl(numbers)[22,000] of the people returned, and #myhl(numbers)[10,000] remained.


  
#versenum(4) And the #myhl(divine)[#smallcaps[Lord]] said to #myhl(men)[Gideon], “The people are still too many. Take them down to the water, and I will test them for you there, and anyone of whom I say to you, ‘This one shall go with you,’ shall go with you, and anyone of whom I say to you, ‘This one shall not go with you,’ shall not go.” 
#versenum(5) So he brought the people down to the water. And the #myhl(divine)[#smallcaps[Lord]] said to #myhl(men)[Gideon], “Every one who laps the water with his tongue, as a #underline[dog] laps, you shall set by himself. Likewise, every one who #underline[kneels] down to drink.” 
#versenum(6) And the number of those who lapped, putting their hands to their #underline[mouths], was #myhl(numbers)[300] men, but all the rest of the people #underline[knelt] down to drink water. 
#versenum(7) And the #myhl(divine)[#smallcaps[Lord]] said to #myhl(men)[Gideon], “With the #myhl(numbers)[300] men who lapped I will save you and give the #myhl(other)[Midianites] into your hand, and let all the others go every man to his home.” 
#versenum(8) So the people took provisions in their hands, and their trumpets. And he sent all the rest of #myhl(people-groups)[Israel] every man to his tent, but #underline[retained] the #myhl(numbers)[300] men. And #myhl(places)[the camp of Midian] was below him in the valley.


  
#versenum(9) That same night the #myhl(divine)[#smallcaps[Lord]] said to him, “Arise, go down against the camp, for I have given it into your hand. 
#versenum(10) But if you are afraid to go down, go down to the camp with #myhl(men)[Purah] your servant. 
#versenum(11) And you shall hear what they say, and afterward your hands shall be strengthened to go down against the camp.” Then he went down with #myhl(men)[Purah] his servant to the #underline[outposts] of the armed men who were in the camp. 
#versenum(12) And the #myhl(other)[Midianites] and the #myhl(other)[Amalekites] and all the people of the East lay along the valley like locusts in abundance, and their camels were without number, as the sand that is on the seashore in abundance. 
#versenum(13) When #myhl(men)[Gideon] came, behold, a man was telling a dream to his comrade. And he said, “Behold, I #underline[dreamed] a dream, and behold, a #underline[cake] of barley bread #underline[tumbled] into #myhl(places)[the camp of Midian] and came to the tent and struck it so that it fell and turned it #underline[upside] down, so that the tent lay flat.” 
#versenum(14) And his comrade answered, “This is no other than the sword of #myhl(men)[Gideon] the son of #myhl(men)[Joash], a man of #myhl(people-groups)[Israel]; #myhl(divine)[God] has given into his hand #myhl(places)[Midian] and all the camp.”


  
#versenum(15) As soon as #myhl(men)[Gideon] heard the telling of the dream and its #underline[interpretation], he worshiped. And he returned to #myhl(places)[the camp of Israel] and said, “Arise, for the #myhl(divine)[#smallcaps[Lord]] has given the #underline[host] of #myhl(places)[Midian] into your hand.” 
#versenum(16) And he divided the #myhl(numbers)[300] men into #myhl(numbers)[three] companies and put trumpets into the hands of all of them and empty jars, with torches inside the jars. 
#versenum(17) And he said to them, “Look at me, and do likewise. When I come to the outskirts of the camp, do as I do. 
#versenum(18) When I blow the trumpet, I and all who are with me, then blow the trumpets also on every side of all the camp and shout, ‘For the #myhl(divine)[#smallcaps[Lord]] and for #myhl(men)[Gideon].’”


  
#section-heading[Gideon Defeats Midian]


#versenum(19) So #myhl(men)[Gideon] and the #myhl(numbers)[hundred] men who were with him came to the outskirts of the camp at the beginning of the middle watch, when they had just set the watch. And they blew the trumpets and #underline[smashed] the jars that were in their hands. 
#versenum(20) Then the #myhl(numbers)[three] companies blew the trumpets and broke the jars. They held in their left hands the torches, and in their right hands the trumpets to blow. And they cried out, “A sword for the #myhl(divine)[#smallcaps[Lord]] and for #myhl(men)[Gideon]!” 
#versenum(21) Every man stood in his place around the camp, and all the army ran. They cried out and fled. 
#versenum(22) When they blew the #myhl(numbers)[300] trumpets, the #myhl(divine)[#smallcaps[Lord]] set every man’s sword against his comrade and against all the army. And the army fled as far as #underline[#myhl(places)[Beth-shittah]] toward #underline[#myhl(places)[Zererah]],#footnote[Judges 7:22 Some Hebrew manuscripts #emph[Zeredah]] as far as the border of #underline[#myhl(places)[Abel-meholah]], by #underline[#myhl(places)[Tabbath]]. 
#versenum(23) And the #myhl(people-groups)[men of Israel] were called out from #myhl(other)[Naphtali] and from #myhl(other)[Asher] and from all #myhl(other)[Manasseh], and they pursued after #myhl(places)[Midian].


  
#versenum(24) #myhl(men)[Gideon] sent messengers throughout all #myhl(places)[the hill country of Ephraim], saying, “Come down against the #myhl(other)[Midianites] and #underline[capture] the waters against them, as far as #myhl(places)[Beth-barah], and also the #myhl(places)[Jordan].” So all the men of #myhl(other)[Ephraim] were called out, and they captured the waters as far as #myhl(places)[Beth-barah], and also the #myhl(places)[Jordan]. 
#versenum(25) And they captured the #myhl(numbers)[two] princes of #myhl(places)[Midian], #myhl(men)[Oreb] and #myhl(men)[Zeeb]. They killed #myhl(men)[Oreb] at the #myhl(places)[rock of Oreb], and #myhl(men)[Zeeb] they killed at the #myhl(places)[winepress of Zeeb]. Then they pursued #myhl(places)[Midian], and they brought the heads of #myhl(men)[Oreb] and #myhl(men)[Zeeb] to #myhl(men)[Gideon] #underline[across] the #myhl(places)[Jordan].


  
#chapter-heading[Judges 8]


#section-heading[Gideon Defeats Zebah and Zalmunna]


#versenum(1) Then the men of #myhl(other)[Ephraim] said to him, “What is this that you have done to us, not to call us when you went to fight against #myhl(places)[Midian]?” And they #underline[accused] him #underline[fiercely]. 
#versenum(2) And he said to them, “What have I done now in comparison with you? Is not the gleaning of the grapes of #myhl(other)[Ephraim] better than the #underline[grape] harvest of #myhl(men)[Abiezer]? 
#versenum(3) #myhl(divine)[God] has given into your hands the princes of #myhl(places)[Midian], #myhl(men)[Oreb] and #myhl(men)[Zeeb]. What have I been able to do in comparison with you?” Then their anger#footnote[Judges 8:3 Hebrew #emph[their spirit]] against him #underline[subsided] when he said this.


  
#versenum(4) And #myhl(men)[Gideon] came to the #myhl(places)[Jordan] and crossed over, he and the #myhl(numbers)[300] men who were with him, exhausted yet pursuing. 
#versenum(5) So he said to the men of #myhl(places)[Succoth], “Please give #underline[loaves] of bread to the people who follow me, for they are exhausted, and I am pursuing after #myhl(men)[Zebah] and #myhl(men)[Zalmunna], the kings of #myhl(places)[Midian].” 
#versenum(6) And the officials of #myhl(places)[Succoth] said, “Are the hands of #myhl(men)[Zebah] and #myhl(men)[Zalmunna] already in your hand, that we should give bread to your army?” 
#versenum(7) So #myhl(men)[Gideon] said, “Well then, when the #myhl(divine)[#smallcaps[Lord]] has given #myhl(men)[Zebah] and #myhl(men)[Zalmunna] into my hand, I will #underline[flail] your flesh with the thorns of the wilderness and with briers.” 
#versenum(8) And from there he went up to #myhl(places)[Penuel], and spoke to them in the same way, and the men of #myhl(places)[Penuel] answered him as the men of #myhl(places)[Succoth] had answered. 
#versenum(9) And he said to the men of #myhl(places)[Penuel], “When I come again in peace, I will break down this tower.”


  
#versenum(10) Now #myhl(men)[Zebah] and #myhl(men)[Zalmunna] were in #underline[#myhl(places)[Karkor]] with their army, about #underline[#myhl(numbers)[15,000]] men, all who were left of all the army of the people of the East, for there had fallen #underline[#myhl(numbers)[120,000]] men who drew the sword. 
#versenum(11) And #myhl(men)[Gideon] went up by the way of the tent #underline[dwellers] east of #underline[#myhl(places)[Nobah]] and #underline[#myhl(places)[Jogbehah]] and #underline[attacked] the army, for the army #underline[felt] #underline[secure]. 
#versenum(12) And #myhl(men)[Zebah] and #myhl(men)[Zalmunna] fled, and he pursued them and captured the #myhl(numbers)[two] kings of #myhl(places)[Midian], #myhl(men)[Zebah] and #myhl(men)[Zalmunna], and he threw all the army into a panic.


  
#versenum(13) Then #myhl(men)[Gideon] the son of #myhl(men)[Joash] returned from the battle by the ascent of #myhl(places)[Heres]. 
#versenum(14) And he captured a young man of #myhl(places)[Succoth] and #underline[questioned] him. And he wrote down for him the officials and elders of #myhl(places)[Succoth], #underline[#myhl(numbers)[seventy-seven]] men. 
#versenum(15) And he came to the men of #myhl(places)[Succoth] and said, “Behold #myhl(men)[Zebah] and #myhl(men)[Zalmunna], about whom you #underline[taunted] me, saying, ‘Are the hands of #myhl(men)[Zebah] and #myhl(men)[Zalmunna] already in your hand, that we should give bread to your men who are exhausted?’” 
#versenum(16) And he took the elders of the city, and he took thorns of the wilderness and briers and with them #underline[taught] the men of #myhl(places)[Succoth] a #underline[lesson]. 
#versenum(17) And he broke down the tower of #myhl(places)[Penuel] and killed the men of the city.


  
#versenum(18) Then he said to #myhl(men)[Zebah] and #myhl(men)[Zalmunna], “Where are the men whom you killed at #myhl(places)[Tabor]?” They answered, “As you are, so were they. Every one of them #underline[resembled] the son of a king.” 
#versenum(19) And he said, “They were my brothers, the sons of my mother. As the #myhl(divine)[#smallcaps[Lord]] lives, if you had saved them alive, I would not kill you.” 
#versenum(20) So he said to #underline[#myhl(men)[Jether]] his firstborn, “Rise and kill them!” But the young man did not draw his sword, for he was afraid, because he was still a young man. 
#versenum(21) Then #myhl(men)[Zebah] and #myhl(men)[Zalmunna] said, “Rise yourself and fall upon us, for as the man is, so is his strength.” And #myhl(men)[Gideon] arose and killed #myhl(men)[Zebah] and #myhl(men)[Zalmunna], and he took the crescent ornaments that were on the necks of their camels.


  
#section-heading[Gideon’s Ephod]


#versenum(22) Then the #myhl(people-groups)[men of Israel] said to #myhl(men)[Gideon], “Rule over us, you and your son and your #underline[grandson] also, for you have saved us from the hand of #myhl(places)[Midian].” 
#versenum(23) #myhl(men)[Gideon] said to them, “I will not rule over you, and my son will not rule over you; the #myhl(divine)[#smallcaps[Lord]] will rule over you.” 
#versenum(24) And #myhl(men)[Gideon] said to them, “Let me make a #underline[request] of you: every one of you give me the earrings from his spoil.” (For they had golden earrings, because they were #underline[#myhl(other)[Ishmaelites]].) 
#versenum(25) And they answered, “We will willingly give them.” And they spread a cloak, and every man threw in it the earrings of his spoil. 
#versenum(26) And the weight of the golden earrings that he #underline[requested] was #underline[#myhl(numbers)[1,700]] shekels#footnote[Judges 8:26 A #emph[shekel] was about 2/5 ounce or 11 grams] of gold, besides the crescent ornaments and the #underline[pendants] and the #underline[purple] garments worn by the kings of #myhl(places)[Midian], and besides the #underline[collars] that were around the necks of their camels. 
#versenum(27) And #myhl(men)[Gideon] made an ephod of it and put it in his city, in #myhl(places)[Ophrah]. And all #myhl(people-groups)[Israel] whored after it there, and it became a snare to #myhl(men)[Gideon] and to his family. 
#versenum(28) So #myhl(places)[Midian] was subdued before the #myhl(people-groups)[people of Israel], and they raised their heads no more. And the land had rest for #myhl(numbers)[forty] years in the days of #myhl(men)[Gideon].


  
#section-heading[The Death of Gideon]


#versenum(29) #myhl(men)[Jerubbaal] the son of #myhl(men)[Joash] went and lived in his own house. 
#versenum(30) Now #myhl(men)[Gideon] had #myhl(numbers)[seventy] sons, his own offspring,#footnote[Judges 8:30 Hebrew #emph[who came from his own loins]] for he had many wives. 
#versenum(31) And his concubine who was in #myhl(places)[Shechem] also bore him a son, and he called his name #myhl(men)[Abimelech]. 
#versenum(32) And #myhl(men)[Gideon] the son of #myhl(men)[Joash] died in a good old age and was buried in the tomb of #myhl(men)[Joash] his father, at #myhl(places)[Ophrah] of the #myhl(other)[Abiezrites].


  
#versenum(33) As soon as #myhl(men)[Gideon] died, the #myhl(people-groups)[people of Israel] turned again and whored after the #myhl(other)[Baals] and made #myhl(other)[Baal-berith] their god. 
#versenum(34) And the #myhl(people-groups)[people of Israel] did not remember the #myhl(divine)[#smallcaps[Lord]] their #myhl(divine)[God], who had delivered them from the hand of all their enemies on every side, 
#versenum(35) and they did not show #underline[steadfast] love to the family of #myhl(men)[Jerubbaal] (that is, #myhl(men)[Gideon]) in return for all the good that he had done to #myhl(people-groups)[Israel].


  
#chapter-heading[Judges 9]


#section-heading[Abimelech’s Conspiracy]


#versenum(1) Now #myhl(men)[Abimelech] the son of #myhl(men)[Jerubbaal] went to #myhl(places)[Shechem] to his mother’s relatives and said to them and to the whole clan of his mother’s family, 
#versenum(2) “Say in the ears of all the leaders of #myhl(places)[Shechem], ‘Which is better for you, that all #myhl(numbers)[seventy] of the sons of #myhl(men)[Jerubbaal] rule over you, or that one rule over you?’ Remember also that I am your #underline[bone] and your flesh.”


  
#versenum(3) And his mother’s relatives spoke all these words on his #underline[behalf] in the ears of all the leaders of #myhl(places)[Shechem], and their hearts #underline[inclined] to follow #myhl(men)[Abimelech], for they said, “He is our brother.” 
#versenum(4) And they gave him #myhl(numbers)[seventy] pieces of silver out of the house of #myhl(other)[Baal-berith] with which #myhl(men)[Abimelech] hired worthless and #underline[reckless] fellows, who followed him. 
#versenum(5) And he went to his father’s house at #myhl(places)[Ophrah] and killed his brothers the sons of #myhl(men)[Jerubbaal], #myhl(numbers)[seventy] men, on one stone. But #myhl(men)[Jotham] the youngest son of #myhl(men)[Jerubbaal] was left, for he hid himself. 
#versenum(6) And all the leaders of #myhl(places)[Shechem] came together, and all #myhl(places)[Beth-millo], and they went and made #myhl(men)[Abimelech] king, by the oak of the #underline[pillar] at #myhl(places)[Shechem].


  
#versenum(7) When it was told to #myhl(men)[Jotham], he went and stood on top of #myhl(places)[Mount Gerizim] and cried #underline[aloud] and said to them, “Listen to me, you leaders of #myhl(places)[Shechem], that #myhl(divine)[God] may listen to you. 
#versenum(8) The trees once went out to anoint a king over them, and they said to the olive tree, ‘Reign over us.’ 
#versenum(9) But the olive tree said to them, ‘Shall I leave my abundance, by which gods and men are #underline[honored], and go hold sway over the trees?’ 
#versenum(10) And the trees said to the fig tree, ‘You come and reign over us.’ 
#versenum(11) But the fig tree said to them, ‘Shall I leave my #underline[sweetness] and my good fruit and go hold sway over the trees?’ 
#versenum(12) And the trees said to the vine, ‘You come and reign over us.’ 
#versenum(13) But the vine said to them, ‘Shall I leave my wine that #underline[cheers] #myhl(divine)[God] and men and go hold sway over the trees?’ 
#versenum(14) Then all the trees said to the bramble, ‘You come and reign over us.’ 
#versenum(15) And the bramble said to the trees, ‘If in good faith you are #underline[anointing] me king over you, then come and take refuge in my #underline[shade], but if not, let fire come out of the bramble and devour the #underline[cedars] of #myhl(places)[Lebanon].’


  
#versenum(16) “Now therefore, if you acted in good faith and integrity when you made #myhl(men)[Abimelech] king, and if you have dealt well with #myhl(men)[Jerubbaal] and his house and have done to him as his deeds #underline[deserved]—
#versenum(17) for my father fought for you and risked his life and delivered you from the hand of #myhl(places)[Midian], 
#versenum(18) and you have #underline[risen] up against my father’s house this day and have killed his sons, #myhl(numbers)[seventy] men on one stone, and have made #myhl(men)[Abimelech], the son of his female servant, king over the leaders of #myhl(places)[Shechem], because he is your relative—
#versenum(19) if you then have acted in good faith and integrity with #myhl(men)[Jerubbaal] and with his house this day, then rejoice in #myhl(men)[Abimelech], and let him also rejoice in you. 
#versenum(20) But if not, let fire come out from #myhl(men)[Abimelech] and devour the leaders of #myhl(places)[Shechem] and #myhl(places)[Beth-millo]; and let fire come out from the leaders of #myhl(places)[Shechem] and from #myhl(places)[Beth-millo] and devour #myhl(men)[Abimelech].” 
#versenum(21) And #myhl(men)[Jotham] ran away and fled and went to #underline[#myhl(places)[Beer]] and lived there, because of #myhl(men)[Abimelech] his brother.


  
#section-heading[The Downfall of Abimelech]


#versenum(22) #myhl(men)[Abimelech] ruled over #myhl(people-groups)[Israel] #myhl(numbers)[three] years. 
#versenum(23) And #myhl(divine)[God] sent an evil spirit between #myhl(men)[Abimelech] and the leaders of #myhl(places)[Shechem], and the leaders of #myhl(places)[Shechem] dealt #underline[treacherously] with #myhl(men)[Abimelech], 
#versenum(24) that the #underline[violence] done to the #myhl(numbers)[seventy] sons of #myhl(men)[Jerubbaal] might come, and their blood be laid on #myhl(men)[Abimelech] their brother, who killed them, and on the men of #myhl(places)[Shechem], who strengthened his hands to kill his brothers. 
#versenum(25) And the leaders of #myhl(places)[Shechem] put men in ambush against him on the mountaintops, and they #underline[robbed] all who passed by them along that way. And it was told to #myhl(men)[Abimelech].


  
#versenum(26) And #myhl(men)[Gaal] the son of #myhl(men)[Ebed] moved into #myhl(places)[Shechem] with his relatives, and the leaders of #myhl(places)[Shechem] put #underline[confidence] in him. 
#versenum(27) And they went out into the field and gathered the grapes from their vineyards and trod them and held a #underline[festival]; and they went into the house of their god and ate and drank and #underline[reviled] #myhl(men)[Abimelech]. 
#versenum(28) And #myhl(men)[Gaal] the son of #myhl(men)[Ebed] said, “Who is #myhl(men)[Abimelech], and who are we of #myhl(places)[Shechem], that we should serve him? Is he not the son of #myhl(men)[Jerubbaal], and is not #myhl(men)[Zebul] his #underline[officer]? Serve the men of #myhl(men)[Hamor] the father of #myhl(places)[Shechem]; but why should we serve him? 
#versenum(29) Would that this people were under my hand! Then I would #underline[remove] #myhl(men)[Abimelech]. I would say#footnote[Judges 9:29 Septuagint; Hebrew #emph[and he said]] to #myhl(men)[Abimelech], ‘#underline[Increase] your army, and come out.’”


  
#versenum(30) When #myhl(men)[Zebul] the #underline[ruler] of the city heard the words of #myhl(men)[Gaal] the son of #myhl(men)[Ebed], his anger was kindled. 
#versenum(31) And he sent messengers to #myhl(men)[Abimelech] secretly,#footnote[Judges 9:31 Or #emph[at Tormah]] saying, “Behold, #myhl(men)[Gaal] the son of #myhl(men)[Ebed] and his relatives have come to #myhl(places)[Shechem], and they are #underline[stirring] up#footnote[Judges 9:31 Hebrew #emph[besieging], or #emph[closing up]] the city against you. 
#versenum(32) Now therefore, go by night, you and the people who are with you, and set an ambush in the field. 
#versenum(33) Then in the morning, as soon as the sun is up, rise early and #underline[rush] upon the city. And when he and the people who are with him come out against you, you may do to them as your hand #underline[finds] to do.”


  
#versenum(34) So #myhl(men)[Abimelech] and all the men who were with him rose up by night and set an ambush against #myhl(places)[Shechem] in #myhl(numbers)[four] companies. 
#versenum(35) And #myhl(men)[Gaal] the son of #myhl(men)[Ebed] went out and stood in the entrance of the gate of the city, and #myhl(men)[Abimelech] and the people who were with him rose from the ambush. 
#versenum(36) And when #myhl(men)[Gaal] saw the people, he said to #myhl(men)[Zebul], “Look, people are coming down from the mountaintops!” And #myhl(men)[Zebul] said to him, “You #underline[mistake]#footnote[Judges 9:36 Hebrew #emph[You see]] the #underline[shadow] of the mountains for men.” 
#versenum(37) #myhl(men)[Gaal] spoke again and said, “Look, people are coming down from the #underline[center] of the land, and one company is coming from the direction of the #myhl(places)[#underline[Diviners]’ Oak].” 
#versenum(38) Then #myhl(men)[Zebul] said to him, “Where is your mouth now, you who said, ‘Who is #myhl(men)[Abimelech], that we should serve him?’ Are not these the people whom you #underline[despised]? Go out now and fight with them.” 
#versenum(39) And #myhl(men)[Gaal] went out at the head of the leaders of #myhl(places)[Shechem] and fought with #myhl(men)[Abimelech]. 
#versenum(40) And #myhl(men)[Abimelech] chased him, and he fled before him. And many fell #underline[wounded], up to the entrance of the gate. 
#versenum(41) And #myhl(men)[Abimelech] lived at #underline[#myhl(places)[Arumah]], and #myhl(men)[Zebul] drove out #myhl(men)[Gaal] and his relatives, so that they could not dwell at #myhl(places)[Shechem].


  
#versenum(42) On the following day, the people went out into the field, and #myhl(men)[Abimelech] was told. 
#versenum(43) He took his people and divided them into #myhl(numbers)[three] companies and set an ambush in the fields. And he looked and saw the people coming out of the city. So he rose against them and killed them. 
#versenum(44) #myhl(men)[Abimelech] and the company that was with him rushed forward and stood at the entrance of the gate of the city, while the #myhl(numbers)[two] companies rushed upon all who were in the field and killed them. 
#versenum(45) And #myhl(men)[Abimelech] fought against the city all that day. He captured the city and killed the people who were in it, and he #underline[razed] the city and #underline[sowed] it with salt.


  
#versenum(46) When all the leaders of the Tower of #myhl(places)[Shechem] heard of it, they entered the stronghold of the house of #underline[#myhl(other)[El-berith]]. 
#versenum(47) #myhl(men)[Abimelech] was told that all the leaders of the Tower of #myhl(places)[Shechem] were gathered together. 
#versenum(48) And #myhl(men)[Abimelech] went up to #myhl(places)[Mount #underline[Zalmon]], he and all the people who were with him. And #myhl(men)[Abimelech] took an #underline[axe] in his hand and cut down a bundle of #underline[brushwood] and took it up and laid it on his shoulder. And he said to the men who were with him, “What you have seen me do, hurry and do as I have done.” 
#versenum(49) So every one of the people cut down his bundle and following #myhl(men)[Abimelech] put it against the stronghold, and they set the stronghold on fire over them, so that all the people of the Tower of #myhl(places)[Shechem] also died, about #myhl(numbers)[1,000] men and women.


  
#versenum(50) Then #myhl(men)[Abimelech] went to #myhl(places)[Thebez] and encamped against #myhl(places)[Thebez] and captured it. 
#versenum(51) But there was a strong tower within the city, and all the men and women and all the leaders of the city fled to it and shut themselves in, and they went up to the roof of the tower. 
#versenum(52) And #myhl(men)[Abimelech] came to the tower and fought against it and drew near to the door of the tower to burn it with fire. 
#versenum(53) And a certain woman threw an upper #underline[millstone] on #underline[#myhl(men)[Abimelech]’s] head and crushed his #underline[skull]. 
#versenum(54) Then he called quickly to the young man his #underline[armor-bearer] and said to him, “Draw your sword and kill me, lest they say of me, ‘A woman killed him.’” And his young man thrust him through, and he died. 
#versenum(55) And when the #myhl(people-groups)[men of Israel] saw that #myhl(men)[Abimelech] was dead, everyone departed to his home. 
#versenum(56) Thus #myhl(divine)[God] returned the evil of #myhl(men)[Abimelech], which he committed against his father in killing his #myhl(numbers)[seventy] brothers. 
#versenum(57) And #myhl(divine)[God] also made all the evil of the men of #myhl(places)[Shechem] return on their heads, and upon them came the curse of #myhl(men)[Jotham] the son of #myhl(men)[Jerubbaal].


  
#chapter-heading[Judges 10]


#section-heading[Tola and Jair]


#versenum(1) After #myhl(men)[Abimelech] there arose to save #myhl(people-groups)[Israel] #underline[#myhl(men)[Tola]] the son of #underline[#myhl(men)[Puah]], son of #underline[#myhl(men)[Dodo]], a man of #myhl(other)[Issachar], and he lived at #myhl(places)[Shamir] in #myhl(places)[the hill country of Ephraim]. 
#versenum(2) And he judged #myhl(people-groups)[Israel] #underline[#myhl(numbers)[twenty-three]] years. Then he died and was buried at #myhl(places)[Shamir].


  
#versenum(3) After him arose #myhl(men)[Jair] the #myhl(other)[Gileadite], who judged #myhl(people-groups)[Israel] #myhl(numbers)[twenty-two] years. 
#versenum(4) And he had #myhl(numbers)[thirty] sons who rode on #myhl(numbers)[thirty] donkeys, and they had #myhl(numbers)[thirty] cities, called #underline[#myhl(places)[Havvoth-jair]] to this day, which are in the land of #myhl(places)[Gilead]. 
#versenum(5) And #myhl(men)[Jair] died and was buried in #underline[#myhl(places)[Kamon]].


  
#section-heading[Further Disobedience and Oppression]


#versenum(6) The #myhl(people-groups)[people of Israel] again did what was evil in the sight of the #myhl(divine)[#smallcaps[Lord]] and served the #myhl(other)[Baals] and the #myhl(other)[Ashtaroth], the gods of #underline[#myhl(places)[Syria]], the gods of #myhl(places)[Sidon], the gods of #myhl(places)[Moab], the gods of the #myhl(other)[Ammonites], and the gods of the #myhl(other)[Philistines]. And they #underline[forsook] the #myhl(divine)[#smallcaps[Lord]] and did not serve him. 
#versenum(7) So the anger of the #myhl(divine)[#smallcaps[Lord]] was kindled against #myhl(people-groups)[Israel], and he sold them into the hand of the #myhl(other)[Philistines] and into the hand of the #myhl(other)[Ammonites], 
#versenum(8) and they crushed and oppressed the #myhl(people-groups)[people of Israel] that year. For #myhl(numbers)[eighteen] years they oppressed all the #myhl(people-groups)[people of Israel] who were beyond the #myhl(places)[Jordan] in the land of the #myhl(other)[Amorites], which is in #myhl(places)[Gilead]. 
#versenum(9) And the #myhl(other)[Ammonites] crossed the #myhl(places)[Jordan] to fight also against #myhl(other)[Judah] and against #myhl(men)[Benjamin] and against the house of #myhl(other)[Ephraim], so that #myhl(people-groups)[Israel] was #underline[severely] #underline[distressed].


  
#versenum(10) And the #myhl(people-groups)[people of Israel] cried out to the #myhl(divine)[#smallcaps[Lord]], saying, “We have sinned against you, because we have forsaken our #myhl(divine)[God] and have served the #myhl(other)[Baals].” 
#versenum(11) And the #myhl(divine)[#smallcaps[Lord]] said to the #myhl(people-groups)[people of Israel], “Did I not save you from the #myhl(people-groups)[Egyptians] and from the #myhl(other)[Amorites], from the #myhl(other)[Ammonites] and from the #myhl(other)[Philistines]? 
#versenum(12) The #myhl(other)[Sidonians] also, and the #myhl(other)[Amalekites] and the #underline[#myhl(other)[Maonites]] oppressed you, and you cried out to me, and I saved you out of their hand. 
#versenum(13) Yet you have forsaken me and served other gods; therefore I will save you no more. 
#versenum(14) Go and #underline[cry] out to the gods whom you have chosen; let them save you in the time of your distress.” 
#versenum(15) And the #myhl(people-groups)[people of Israel] said to the #myhl(divine)[#smallcaps[Lord]], “We have sinned; do to us whatever seems good to you. Only please deliver us this day.” 
#versenum(16) So they put away the foreign gods from among them and served the #myhl(divine)[#smallcaps[Lord]], and he became #underline[impatient] over the #underline[misery] of #myhl(people-groups)[Israel].


  
#versenum(17) Then the #myhl(other)[Ammonites] were called to arms, and they encamped in #myhl(places)[Gilead]. And the #myhl(people-groups)[people of Israel] came together, and they encamped at #myhl(places)[Mizpah]. 
#versenum(18) And the people, the leaders of #myhl(places)[Gilead], said one to another, “Who is the man who will begin to fight against the #myhl(other)[Ammonites]? He shall be head over all the inhabitants of #myhl(places)[Gilead].”


  
#chapter-heading[Judges 11]


#section-heading[Jephthah Delivers Israel]


#versenum(1) Now #myhl(men)[Jephthah] the #myhl(other)[Gileadite] was a mighty #underline[warrior], but he was the son of a prostitute. #myhl(places)[Gilead] was the father of #myhl(men)[Jephthah]. 
#versenum(2) And #underline[#myhl(places)[Gilead]’s] wife also bore him sons. And when his #underline[wife’s] sons grew up, they drove #myhl(men)[Jephthah] out and said to him, “You shall not have an inheritance in our father’s house, for you are the son of another woman.” 
#versenum(3) Then #myhl(men)[Jephthah] fled from his brothers and lived in the land of #myhl(other)[Tob], and worthless fellows #underline[collected] around #myhl(men)[Jephthah] and went out with him.


  
#versenum(4) After a time the #myhl(other)[Ammonites] made war against #myhl(people-groups)[Israel]. 
#versenum(5) And when the #myhl(other)[Ammonites] made war against #myhl(people-groups)[Israel], the elders of #myhl(places)[Gilead] went to bring #myhl(men)[Jephthah] from the land of #myhl(other)[Tob]. 
#versenum(6) And they said to #myhl(men)[Jephthah], “Come and be our leader, that we may fight against the #myhl(other)[Ammonites].” 
#versenum(7) But #myhl(men)[Jephthah] said to the elders of #myhl(places)[Gilead], “Did you not hate me and drive me out of my father’s house? Why have you come to me now when you are in distress?” 
#versenum(8) And the elders of #myhl(places)[Gilead] said to #myhl(men)[Jephthah], “That is why we have turned to you now, that you may go with us and fight against the #myhl(other)[Ammonites] and be our head over all the inhabitants of #myhl(places)[Gilead].” 
#versenum(9) #myhl(men)[Jephthah] said to the elders of #myhl(places)[Gilead], “If you bring me home again to fight against the #myhl(other)[Ammonites], and the #myhl(divine)[#smallcaps[Lord]] gives them over to me, I will be your head.” 
#versenum(10) And the elders of #myhl(places)[Gilead] said to #myhl(men)[Jephthah], “The #myhl(divine)[#smallcaps[Lord]] will be witness between us, if we do not do as you say.” 
#versenum(11) So #myhl(men)[Jephthah] went with the elders of #myhl(places)[Gilead], and the people made him head and leader over them. And #myhl(men)[Jephthah] spoke all his words before the #myhl(divine)[#smallcaps[Lord]] at #myhl(places)[Mizpah].


  
#versenum(12) Then #myhl(men)[Jephthah] sent messengers to the king of the #myhl(other)[Ammonites] and said, “What do you have against me, that you have come to me to fight against my land?” 
#versenum(13) And the king of the #myhl(other)[Ammonites] answered the messengers of #myhl(men)[Jephthah], “Because #myhl(people-groups)[Israel] on coming up from #myhl(places)[Egypt] took away my land, from the #myhl(places)[Arnon] to the #myhl(places)[Jabbok] and to the #myhl(places)[Jordan]; now therefore restore it #underline[peaceably].” 
#versenum(14) #myhl(men)[Jephthah] again sent messengers to the king of the #myhl(other)[Ammonites] 
#versenum(15) and said to him, “Thus says #myhl(men)[Jephthah]: #myhl(people-groups)[Israel] did not take away the land of #myhl(places)[Moab] or the land of the #myhl(other)[Ammonites], 
#versenum(16) but when they came up from #myhl(places)[Egypt], #myhl(people-groups)[Israel] went through the wilderness to the #myhl(places)[Red Sea] and came to #myhl(places)[Kadesh]. 
#versenum(17) #myhl(people-groups)[Israel] then sent messengers to the king of #myhl(places)[Edom], saying, ‘Please let us pass through your land,’ but the king of #myhl(places)[Edom] would not listen. And they sent also to the king of #myhl(places)[Moab], but he would not #underline[consent]. So #myhl(people-groups)[Israel] remained at #myhl(places)[Kadesh].


  
#versenum(18) “Then they journeyed through the wilderness and went around the land of #myhl(places)[Edom] and the land of #myhl(places)[Moab] and arrived on the east side of the land of #myhl(places)[Moab] and #underline[camped] on the other side of the #myhl(places)[Arnon]. But they did not enter the territory of #myhl(places)[Moab], for the #myhl(places)[Arnon] was the boundary of #myhl(places)[Moab]. 
#versenum(19) #myhl(people-groups)[Israel] then sent messengers to #myhl(men)[Sihon] king of the #myhl(other)[Amorites], king of #myhl(places)[Heshbon], and #myhl(people-groups)[Israel] said to him, ‘Please let us pass through your land to our country,’ 
#versenum(20) but #myhl(men)[Sihon] did not #underline[trust] #myhl(people-groups)[Israel] to pass through his territory, so #myhl(men)[Sihon] gathered all his people together and encamped at #myhl(places)[Jahaz] and fought with #myhl(people-groups)[Israel]. 
#versenum(21) And the #myhl(divine)[#smallcaps[Lord], the God of Israel], gave #myhl(men)[Sihon] and all his people into the hand of #myhl(people-groups)[Israel], and they defeated them. So #myhl(people-groups)[Israel] took possession of all the land of the #myhl(other)[Amorites], who inhabited that country. 
#versenum(22) And they took possession of all the territory of the #myhl(other)[Amorites] from the #myhl(places)[Arnon] to the #myhl(places)[Jabbok] and from the wilderness to the #myhl(places)[Jordan]. 
#versenum(23) So then the #myhl(divine)[#smallcaps[Lord], the God of Israel], dispossessed the #myhl(other)[Amorites] from before his people #myhl(people-groups)[Israel]; and are you to take possession of them? 
#versenum(24) Will you not possess what #underline[#myhl(other)[Chemosh]] your god gives you to possess? And all that the #myhl(divine)[#smallcaps[Lord]] our #myhl(divine)[God] has dispossessed before us, we will possess. 
#versenum(25) Now are you any better than #myhl(men)[Balak] the son of #myhl(men)[Zippor], king of #myhl(places)[Moab]? Did he ever contend against #myhl(people-groups)[Israel], or did he ever go to war with them? 
#versenum(26) While #myhl(people-groups)[Israel] lived in #myhl(places)[Heshbon] and its villages, and in #myhl(places)[Aroer] and its villages, and in all the cities that are on the banks of the #myhl(places)[Arnon], #myhl(numbers)[300] years, why did you not deliver them within that time? 
#versenum(27) I therefore have not sinned against you, and you do me #underline[wrong] by making war on me. The #myhl(divine)[#smallcaps[Lord]], the Judge, #underline[decide] this day between the #myhl(people-groups)[people of Israel] and the people of #underline[#myhl(other)[Ammon]].” 
#versenum(28) But the king of the #myhl(other)[Ammonites] did not listen to the words of #myhl(men)[Jephthah] that he sent to him.


  
#section-heading[Jephthah’s Tragic Vow]


#versenum(29) Then the #myhl(divine)[Spirit] of the #myhl(divine)[#smallcaps[Lord]] was upon #myhl(men)[Jephthah], and he passed through #myhl(places)[Gilead] and #myhl(places)[Manasseh] and passed on to #myhl(places)[Mizpah] of #myhl(places)[Gilead], and from #myhl(places)[Mizpah] of #myhl(places)[Gilead] he passed on to the #myhl(other)[Ammonites]. 
#versenum(30) And #myhl(men)[Jephthah] made a vow to the #myhl(divine)[#smallcaps[Lord]] and said, “If you will give the #myhl(other)[Ammonites] into my hand, 
#versenum(31) then whatever#footnote[Judges 11:31 Or #emph[whoever]] comes out from the doors of my house to meet me when I return in peace from the #myhl(other)[Ammonites] shall be the #myhl(divine)[#smallcaps[Lord]]’s, and I will offer it#footnote[Judges 11:31 Or #emph[him]] up for a burnt offering.” 
#versenum(32) So #myhl(men)[Jephthah] crossed over to the #myhl(other)[Ammonites] to fight against them, and the #myhl(divine)[#smallcaps[Lord]] gave them into his hand. 
#versenum(33) And he struck them from #myhl(places)[Aroer] to the neighborhood of #underline[#myhl(places)[Minnith]], #myhl(numbers)[twenty] cities, and as far as #underline[#myhl(places)[Abel-keramim]], with a great blow. So the #myhl(other)[Ammonites] were subdued before the #myhl(people-groups)[people of Israel].


  
#versenum(34) Then #myhl(men)[Jephthah] came to his home at #myhl(places)[Mizpah]. And behold, his daughter came out to meet him with #underline[tambourines] and with dances. She was his only child; besides her he had neither son nor daughter. 
#versenum(35) And as soon as he saw her, he tore his clothes and said, “Alas, my daughter! You have brought me very low, and you have become the cause of great trouble to me. For I have opened my mouth to the #myhl(divine)[#smallcaps[Lord]], and I cannot take back my vow.” 
#versenum(36) And she said to him, “My father, you have opened your mouth to the #myhl(divine)[#smallcaps[Lord]]; do to me according to what has gone out of your mouth, now that the #myhl(divine)[#smallcaps[Lord]] has avenged you on your enemies, on the #myhl(other)[Ammonites].” 
#versenum(37) So she said to her father, “Let this thing be done for me: leave me alone #myhl(numbers)[two] months, that I may go up and down on the mountains and #underline[weep] for my virginity, I and my companions.” 
#versenum(38) So he said, “Go.” Then he sent her away for #myhl(numbers)[two] months, and she departed, she and her companions, and wept for her virginity on the mountains. 
#versenum(39) And at the end of #myhl(numbers)[two] months, she returned to her father, who did with her according to his vow that he had made. She had never known a man, and it became a custom in #myhl(places)[Israel] 
#versenum(40) that the daughters of #myhl(people-groups)[Israel] went year by year to #underline[lament] the daughter of #myhl(men)[Jephthah] the #myhl(other)[Gileadite] #myhl(numbers)[four] days in the year.


  
#chapter-heading[Judges 12]


#section-heading[Jephthah’s Conflict with Ephraim]


#versenum(1) The men of #myhl(other)[Ephraim] were called to arms, and they crossed to #myhl(places)[Zaphon] and said to #myhl(men)[Jephthah], “Why did you #underline[cross] over to fight against the #myhl(other)[Ammonites] and did not call us to go with you? We will burn your house over you with fire.” 
#versenum(2) And #myhl(men)[Jephthah] said to them, “I and my people had a great #underline[dispute] with the #myhl(other)[Ammonites], and when I called you, you did not save me from their hand. 
#versenum(3) And when I saw that you would not save me, I took my life in my hand and crossed over against the #myhl(other)[Ammonites], and the #myhl(divine)[#smallcaps[Lord]] gave them into my hand. Why then have you come up to me this day to fight against me?” 
#versenum(4) Then #myhl(men)[Jephthah] gathered all the men of #myhl(places)[Gilead] and fought with #myhl(other)[Ephraim]. And the men of #myhl(places)[Gilead] struck #myhl(other)[Ephraim], because they said, “You are fugitives of #myhl(other)[Ephraim], you #myhl(other)[Gileadites], in the midst of #myhl(other)[Ephraim] and #myhl(other)[Manasseh].” 
#versenum(5) And the #myhl(other)[Gileadites] captured the fords of the #myhl(places)[Jordan] against the #myhl(other)[Ephraimites]. And when any of the fugitives of #myhl(other)[Ephraim] said, “Let me go over,” the men of #myhl(places)[Gilead] said to him, “Are you an #underline[#myhl(other)[Ephraimite]]?” When he said, “No,” 
#versenum(6) they said to him, “Then say #underline[Shibboleth],” and he said, “#underline[Sibboleth],” for he could not #underline[pronounce] it right. Then they seized him and #underline[slaughtered] him at the fords of the #myhl(places)[Jordan]. At that time #underline[#myhl(numbers)[42,000]] of the #myhl(other)[Ephraimites] fell.


  
#versenum(7) #myhl(men)[Jephthah] judged #myhl(people-groups)[Israel] #myhl(numbers)[six] years. Then #myhl(men)[Jephthah] the #myhl(other)[Gileadite] died and was buried in his city in #myhl(places)[Gilead].#footnote[Judges 12:7 Septuagint; Hebrew #emph[in the cities of Gilead]]


  
#section-heading[Ibzan, Elon, and Abdon]


#versenum(8) After him #myhl(men)[Ibzan] of #myhl(places)[Bethlehem] judged #myhl(people-groups)[Israel]. 
#versenum(9) He had #myhl(numbers)[thirty] sons, and #myhl(numbers)[thirty] daughters he gave in marriage outside his clan, and #myhl(numbers)[thirty] daughters he brought in from outside for his sons. And he judged #myhl(people-groups)[Israel] #myhl(numbers)[seven] years. 
#versenum(10) Then #myhl(men)[Ibzan] died and was buried at #myhl(places)[Bethlehem].


  
#versenum(11) After him #myhl(men)[Elon] the #myhl(other)[Zebulunite] judged #myhl(people-groups)[Israel], and he judged #myhl(people-groups)[Israel] #myhl(numbers)[ten] years. 
#versenum(12) Then #myhl(men)[Elon] the #myhl(other)[Zebulunite] died and was buried at #myhl(places)[Aijalon] in the land of #myhl(other)[Zebulun].


  
#versenum(13) After him #myhl(men)[Abdon] the son of #myhl(men)[Hillel] the #myhl(other)[Pirathonite] judged #myhl(people-groups)[Israel]. 
#versenum(14) He had #myhl(numbers)[forty] sons and #myhl(numbers)[thirty] #underline[grandsons], who rode on #myhl(numbers)[seventy] donkeys, and he judged #myhl(people-groups)[Israel] #myhl(numbers)[eight] years. 
#versenum(15) Then #myhl(men)[Abdon] the son of #myhl(men)[Hillel] the #myhl(other)[Pirathonite] died and was buried at #underline[#myhl(places)[Pirathon]] in the land of #myhl(other)[Ephraim], in the hill country of the #myhl(other)[Amalekites].


  
#chapter-heading[Judges 13]


#section-heading[The Birth of Samson]


#versenum(1) And the #myhl(people-groups)[people of Israel] again did what was evil in the sight of the #myhl(divine)[#smallcaps[Lord]], so the #myhl(divine)[#smallcaps[Lord]] gave them into the hand of the #myhl(other)[Philistines] for #myhl(numbers)[forty] years.


  
#versenum(2) There was a certain man of #myhl(places)[Zorah], of the tribe of the #myhl(other)[Danites], whose name was #myhl(men)[Manoah]. And his wife was barren and had no children. 
#versenum(3) And the #myhl(divine)[angel of the #smallcaps[Lord]] appeared to the woman and said to her, “Behold, you are barren and have not #underline[borne] children, but you shall conceive and bear a son. 
#versenum(4) Therefore be careful and drink no wine or strong drink, and eat nothing unclean, 
#versenum(5) for behold, you shall conceive and bear a son. No razor shall come upon his head, for the child shall be a #myhl(other)[Nazirite] to #myhl(divine)[God] from the womb, and he shall begin to save #myhl(people-groups)[Israel] from the hand of the #myhl(other)[Philistines].” 
#versenum(6) Then the woman came and told her husband, “A man of #myhl(divine)[God] came to me, and his appearance was like the appearance of the angel of #myhl(divine)[God], very #underline[awesome]. I did not ask him where he was from, and he did not tell me his name, 
#versenum(7) but he said to me, ‘Behold, you shall conceive and bear a son. So then drink no wine or strong drink, and eat nothing unclean, for the child shall be a #myhl(other)[Nazirite] to #myhl(divine)[God] from the womb to the day of his death.’”


  
#versenum(8) Then #myhl(men)[Manoah] #underline[prayed] to the #myhl(divine)[#smallcaps[Lord]] and said, “O #myhl(divine)[Lord], please let the man of #myhl(divine)[God] whom you sent come again to us and teach us what we are to do with the child who will be born.” 
#versenum(9) And #myhl(divine)[God] #underline[listened] to the voice of #myhl(men)[Manoah], and the angel of #myhl(divine)[God] came again to the woman as she sat in the field. But #myhl(men)[Manoah] her husband was not with her. 
#versenum(10) So the woman ran quickly and told her husband, “Behold, the man who came to me the other day has appeared to me.” 
#versenum(11) And #myhl(men)[Manoah] arose and went after his wife and came to the man and said to him, “Are you the man who spoke to this woman?” And he said, “I am.” 
#versenum(12) And #myhl(men)[Manoah] said, “Now when your words come true, what is to be the #underline[child’s] manner of life, and what is his #underline[mission]?” 
#versenum(13) And the #myhl(divine)[angel of the #smallcaps[Lord]] said to #myhl(men)[Manoah], “Of all that I said to the woman let her be careful. 
#versenum(14) She may not eat of anything that comes from the vine, neither let her drink wine or strong drink, or eat any unclean thing. All that I commanded her let her observe.”


  
#versenum(15) #myhl(men)[Manoah] said to the #myhl(divine)[angel of the #smallcaps[Lord]], “Please let us detain you and prepare a young goat for you.” 
#versenum(16) And the #myhl(divine)[angel of the #smallcaps[Lord]] said to #myhl(men)[Manoah], “If you detain me, I will not eat of your food. But if you prepare a burnt offering, then offer it to the #myhl(divine)[#smallcaps[Lord]].” (For #myhl(men)[Manoah] did not know that he was the #myhl(divine)[angel of the #smallcaps[Lord]].) 
#versenum(17) And #myhl(men)[Manoah] said to the #myhl(divine)[angel of the #smallcaps[Lord]], “What is your name, so that, when your words come true, we may #underline[honor] you?” 
#versenum(18) And the #myhl(divine)[angel of the #smallcaps[Lord]] said to him, “Why do you ask my name, #underline[seeing] it is wonderful?” 
#versenum(19) So #myhl(men)[Manoah] took the young goat with the grain offering, and offered it on the rock to the #myhl(divine)[#smallcaps[Lord]], to the one who #underline[works]#footnote[Judges 13:19 Septuagint, Vulgate; Hebrew #emph[Lord, and working]] wonders, and #myhl(men)[Manoah] and his wife were watching. 
#versenum(20) And when the flame went up toward heaven from the altar, the #myhl(divine)[angel of the #smallcaps[Lord]] went up in the flame of the altar. Now #myhl(men)[Manoah] and his wife were watching, and they fell on their faces to the ground.


  
#versenum(21) The #myhl(divine)[angel of the #smallcaps[Lord]] appeared no more to #myhl(men)[Manoah] and to his wife. Then #myhl(men)[Manoah] knew that he was the #myhl(divine)[angel of the #smallcaps[Lord]]. 
#versenum(22) And #myhl(men)[Manoah] said to his wife, “We shall surely die, for we have seen #myhl(divine)[God].” 
#versenum(23) But his wife said to him, “If the #myhl(divine)[#smallcaps[Lord]] had meant to kill us, he would not have #underline[accepted] a burnt offering and a grain offering at our hands, or #underline[shown] us all these things, or now #underline[announced] to us such things as these.” 
#versenum(24) And the woman bore a son and called his name #myhl(men)[Samson]. And the young man grew, and the #myhl(divine)[#smallcaps[Lord]] blessed him. 
#versenum(25) And the #myhl(divine)[Spirit] of the #myhl(divine)[#smallcaps[Lord]] began to #underline[stir] him in #myhl(places)[Mahaneh-dan], between #myhl(places)[Zorah] and #myhl(places)[Eshtaol].


  
#chapter-heading[Judges 14]


#section-heading[Samson’s Marriage]


#versenum(1) #myhl(men)[Samson] went down to #myhl(places)[Timnah], and at #myhl(places)[Timnah] he saw one of the daughters of the #myhl(other)[Philistines]. 
#versenum(2) Then he came up and told his father and mother, “I saw one of the daughters of the #myhl(other)[Philistines] at #myhl(places)[Timnah]. Now get her for me as my wife.” 
#versenum(3) But his father and mother said to him, “Is there not a woman among the daughters of your relatives, or among all our people, that you must go to take a wife from the uncircumcised #myhl(other)[Philistines]?” But #myhl(men)[Samson] said to his father, “Get her for me, for she is right in my eyes.”


  
#versenum(4) His father and mother did not know that it was from the #myhl(divine)[#smallcaps[Lord]], for he was seeking an #underline[opportunity] against the #myhl(other)[Philistines]. At that time the #myhl(other)[Philistines] ruled over #myhl(people-groups)[Israel].


  
#versenum(5) Then #myhl(men)[Samson] went down with his father and mother to #myhl(places)[Timnah], and they came to the vineyards of #myhl(places)[Timnah]. And behold, a young lion came toward him #underline[roaring]. 
#versenum(6) Then the #myhl(divine)[Spirit] of the #myhl(divine)[#smallcaps[Lord]] rushed upon him, and although he had nothing in his hand, he tore the lion in pieces as one #underline[tears] a young goat. But he did not tell his father or his mother what he had done. 
#versenum(7) Then he went down and #underline[talked] with the woman, and she was right in #myhl(men)[Samson]’s eyes.


  
#versenum(8) After some days he returned to take her. And he turned aside to see the carcass of the lion, and behold, there was a #underline[swarm] of #underline[bees] in the body of the lion, and honey. 
#versenum(9) He scraped it out into his hands and went on, eating as he went. And he came to his father and mother and gave some to them, and they ate. But he did not tell them that he had scraped the honey from the carcass of the lion.


  
#versenum(10) His father went down to the woman, and #myhl(men)[Samson] prepared a feast there, for so the young men used to do. 
#versenum(11) As soon as the people saw him, they brought #myhl(numbers)[thirty] companions to be with him. 
#versenum(12) And #myhl(men)[Samson] said to them, “Let me now put a riddle to you. If you can tell me what it is, within the #myhl(numbers)[seven] days of the feast, and find it out, then I will give you #myhl(numbers)[thirty] linen garments and #myhl(numbers)[thirty] changes of clothes, 
#versenum(13) but if you cannot tell me what it is, then you shall give me #myhl(numbers)[thirty] linen garments and #myhl(numbers)[thirty] changes of clothes.” And they said to him, “Put your riddle, that we may hear it.” 
#versenum(14) And he said to them,


    “Out of the #underline[eater] came something to eat.\
    Out of the strong came something #underline[sweet].”\


      And in #myhl(numbers)[three] days they could not #underline[solve] the riddle.


  
#versenum(15) On the #myhl(numbers)[fourth]#footnote[Judges 14:15 Septuagint, Syriac; Hebrew #emph[seventh]] day they said to #myhl(men)[Samson]’s wife, “#underline[Entice] your husband to tell us what the riddle is, lest we burn you and your father’s house with fire. Have you invited us here to #underline[impoverish] us?” 
#versenum(16) And #myhl(men)[Samson]’s wife wept over him and said, “You only hate me; you do not love me. You have put a riddle to my people, and you have not told me what it is.” And he said to her, “Behold, I have not told my father nor my mother, and shall I tell you?” 
#versenum(17) She wept before him the #myhl(numbers)[seven] days that their feast #underline[lasted], and on the #myhl(numbers)[seventh] day he told her, because she pressed him hard. Then she told the riddle to her people. 
#versenum(18) And the men of the city said to him on the #myhl(numbers)[seventh] day before the sun went down,


    “What is #underline[sweeter] than honey?\
    What is #underline[stronger] than a lion?”\


      And he said to them,


    “If you had not #underline[plowed] with my #underline[heifer],\
    you would not have found out my riddle.”\


      
#versenum(19) And the #myhl(divine)[Spirit] of the #myhl(divine)[#smallcaps[Lord]] rushed upon him, and he went down to #myhl(places)[Ashkelon] and struck down #myhl(numbers)[thirty] men of the town and took their spoil and gave the garments to those who had told the riddle. In #underline[hot] anger he went back to his father’s house. 
#versenum(20) And #myhl(men)[Samson]’s wife was given to his companion, who had been his #underline[best] man.


  
#chapter-heading[Judges 15]


#section-heading[Samson Defeats the Philistines]


#versenum(1) After some days, at the time of wheat harvest, #myhl(men)[Samson] went to #underline[visit] his wife with a young goat. And he said, “I will go in to my wife in the chamber.” But her father would not allow him to go in. 
#versenum(2) And her father said, “I #underline[really] thought that you utterly #underline[hated] her, so I gave her to your companion. Is not her younger #underline[sister] more beautiful than she? Please take her #underline[instead].” 
#versenum(3) And #myhl(men)[Samson] said to them, “This time I shall be #underline[innocent] in regard to the #myhl(other)[Philistines], when I do them harm.” 
#versenum(4) So #myhl(men)[Samson] went and caught #myhl(numbers)[300] foxes and took torches. And he turned them tail to tail and put a #underline[torch] between each #underline[pair] of #underline[tails]. 
#versenum(5) And when he had set fire to the torches, he let the foxes go into the standing grain of the #myhl(other)[Philistines] and set fire to the #underline[stacked] grain and the standing grain, as well as the olive orchards. 
#versenum(6) Then the #myhl(other)[Philistines] said, “Who has done this?” And they said, “#myhl(men)[Samson], the son-in-law of the #underline[#myhl(other)[Timnite]], because he has taken his wife and given her to his companion.” And the #myhl(other)[Philistines] came up and burned her and her father with fire. 
#versenum(7) And #myhl(men)[Samson] said to them, “If this is what you do, I swear I will be avenged on you, and after that I will #underline[quit].” 
#versenum(8) And he struck them #underline[hip] and thigh with a great blow, and he went down and stayed in the cleft of the #myhl(places)[rock of Etam].


  
#versenum(9) Then the #myhl(other)[Philistines] came up and encamped in #myhl(places)[Judah] and made a #underline[raid] on #myhl(places)[Lehi]. 
#versenum(10) And the men of #myhl(other)[Judah] said, “Why have you come up against us?” They said, “We have come up to bind #myhl(men)[Samson], to do to him as he did to us.” 
#versenum(11) Then #myhl(numbers)[3,000] men of #myhl(other)[Judah] went down to the cleft of the #myhl(places)[rock of Etam], and said to #myhl(men)[Samson], “Do you not know that the #myhl(other)[Philistines] are rulers over us? What then is this that you have done to us?” And he said to them, “As they did to me, so have I done to them.” 
#versenum(12) And they said to him, “We have come down to bind you, that we may give you into the hands of the #myhl(other)[Philistines].” And #myhl(men)[Samson] said to them, “Swear to me that you will not attack me yourselves.” 
#versenum(13) They said to him, “No; we will only bind you and give you into their hands. We will surely not kill you.” So they bound him with #myhl(numbers)[two] new ropes and brought him up from the rock.


  
#versenum(14) When he came to #myhl(places)[Lehi], the #myhl(other)[Philistines] came #underline[shouting] to meet him. Then the #myhl(divine)[Spirit] of the #myhl(divine)[#smallcaps[Lord]] rushed upon him, and the ropes that were on his arms became as flax that has caught fire, and his #underline[bonds] melted off his hands. 
#versenum(15) And he found a fresh jawbone of a donkey, and put out his hand and took it, and with it he struck #myhl(numbers)[1,000] men. 
#versenum(16) And #myhl(men)[Samson] said,


    “With the jawbone of a donkey,\
    #vin heaps upon heaps,\
    with the jawbone of a donkey\
    #vin have I struck down a #myhl(numbers)[thousand] men.”\


      
#versenum(17) As soon as he had finished #underline[speaking], he threw away the jawbone out of his hand. And that place was called #underline[#myhl(places)[Ramath-lehi]].#footnote[Judges 15:17 #emph[Ramath-lehi] means #emph[the hill of the jawbone]]


  
#versenum(18) And he was very thirsty, and he called upon the #myhl(divine)[#smallcaps[Lord]] and said, “You have #underline[granted] this great #underline[salvation] by the hand of your servant, and shall I now die of #underline[thirst] and fall into the hands of the uncircumcised?” 
#versenum(19) And #myhl(divine)[God] #underline[split] open the #underline[hollow] place that is at #myhl(places)[Lehi], and water came out from it. And when he drank, his spirit returned, and he #underline[revived]. Therefore the name of it was called #underline[#myhl(places)[En-hakkore]];#footnote[Judges 15:19 #emph[En-hakkore] means #emph[the spring of him who called]] it is at #myhl(places)[Lehi] to this day. 
#versenum(20) And he judged #myhl(people-groups)[Israel] in the days of the #myhl(other)[Philistines] #myhl(numbers)[twenty] years.


  
#chapter-heading[Judges 16]


#section-heading[Samson and Delilah]


#versenum(1) #myhl(men)[Samson] went to #myhl(places)[Gaza], and there he saw a prostitute, and he went in to her. 
#versenum(2) The #underline[#myhl(other)[Gazites]] were told, “#myhl(men)[Samson] has come here.” And they surrounded the place and set an ambush for him all night at the gate of the city. They kept quiet all night, saying, “Let us wait till the light of the morning; then we will kill him.” 
#versenum(3) But #myhl(men)[Samson] lay till midnight, and at midnight he arose and took hold of the doors of the gate of the city and the #myhl(numbers)[two] #underline[posts], and pulled them up, bar and all, and put them on his #underline[shoulders] and carried them to the top of the hill that is in front of #myhl(places)[Hebron].


  
#versenum(4) After this he #underline[loved] a woman in the #myhl(places)[Valley of #underline[Sorek]], whose name was #myhl(women)[Delilah]. 
#versenum(5) And the lords of the #myhl(other)[Philistines] came up to her and said to her, “#underline[Seduce] him, and see where his great strength lies, and by what #underline[means] we may #underline[overpower] him, that we may bind him to #underline[humble] him. And we will each give you #myhl(numbers)[1,100] pieces of silver.” 
#versenum(6) So #myhl(women)[Delilah] said to #myhl(men)[Samson], “Please tell me where your great strength lies, and how you might be bound, that one could #underline[subdue] you.”


  
#versenum(7) #myhl(men)[Samson] said to her, “If they bind me with #myhl(numbers)[seven] fresh bowstrings that have not been dried, then I shall become weak and be like any other man.” 
#versenum(8) Then the lords of the #myhl(other)[Philistines] brought up to her #myhl(numbers)[seven] fresh bowstrings that had not been dried, and she bound him with them. 
#versenum(9) Now she had men lying in ambush in an inner chamber. And she said to him, “The #myhl(other)[Philistines] are upon you, #myhl(men)[Samson]!” But he snapped the bowstrings, as a thread of flax #underline[snaps] when it touches the fire. So the secret of his strength was not known.


  
#versenum(10) Then #myhl(women)[Delilah] said to #myhl(men)[Samson], “Behold, you have mocked me and told me lies. Please tell me how you might be bound.” 
#versenum(11) And he said to her, “If they bind me with new ropes that have not been used, then I shall become weak and be like any other man.” 
#versenum(12) So #myhl(women)[Delilah] took new ropes and bound him with them and said to him, “The #myhl(other)[Philistines] are upon you, #myhl(men)[Samson]!” And the men lying in ambush were in an inner chamber. But he snapped the ropes off his arms like a thread.


  
#versenum(13) Then #myhl(women)[Delilah] said to #myhl(men)[Samson], “Until now you have mocked me and told me lies. Tell me how you might be bound.” And he said to her, “If you #underline[weave] the #myhl(numbers)[seven] locks of my head with the web and #underline[fasten] it tight with the pin, then I shall become weak and be like any other man.” 
#versenum(14) So while he #underline[slept], #myhl(women)[Delilah] took the #myhl(numbers)[seven] locks of his head and #underline[wove] them into the web.#footnote[Judges 16:14 Compare Septuagint; Hebrew lacks #emph[and fasten it tight . . . into the web]] And she made them tight with the pin and said to him, “The #myhl(other)[Philistines] are upon you, #myhl(men)[Samson]!” But he awoke from his sleep and pulled away the pin, the #underline[loom], and the web.


  
#versenum(15) And she said to him, “How can you say, ‘I love you,’ when your heart is not with me? You have mocked me these #myhl(numbers)[three] times, and you have not told me where your great strength lies.” 
#versenum(16) And when she pressed him hard with her words day after day, and urged him, his soul was #underline[vexed] to death. 
#versenum(17) And he told her all his heart, and said to her, “A razor has never come upon my head, for I have been a #myhl(other)[Nazirite] to #myhl(divine)[God] from my mother’s womb. If my head is shaved, then my strength will leave me, and I shall become weak and be like any other man.”


  
#versenum(18) When #myhl(women)[Delilah] saw that he had told her all his heart, she sent and called the lords of the #myhl(other)[Philistines], saying, “Come up again, for he has told me all his heart.” Then the lords of the #myhl(other)[Philistines] came up to her and brought the money in their hands. 
#versenum(19) She made him sleep on her #underline[knees]. And she called a man and had him #underline[shave] off the #myhl(numbers)[seven] locks of his head. Then she began to #underline[torment] him, and his strength left him. 
#versenum(20) And she said, “The #myhl(other)[Philistines] are upon you, #myhl(men)[Samson]!” And he awoke from his sleep and said, “I will go out as at other times and #underline[shake] myself #underline[free].” But he did not know that the #myhl(divine)[#smallcaps[Lord]] had left him. 
#versenum(21) And the #myhl(other)[Philistines] seized him and #underline[gouged] out his eyes and brought him down to #myhl(places)[Gaza] and bound him with bronze #underline[shackles]. And he ground at the #underline[mill] in the prison. 
#versenum(22) But the hair of his head began to #underline[grow] again after it had been shaved.


  
#section-heading[The Death of Samson]


#versenum(23) Now the lords of the #myhl(other)[Philistines] gathered to offer a great sacrifice to #underline[#myhl(other)[Dagon]] their god and to rejoice, and they said, “Our god has given #myhl(men)[Samson] our enemy into our hand.” 
#versenum(24) And when the people saw him, they #underline[praised] their god. For they said, “Our god has given our enemy into our hand, the #underline[ravager] of our country, who has killed many of us.”#footnote[Judges 16:24 Or #emph[who has multiplied our slain]] 
#versenum(25) And when their hearts were merry, they said, “Call #myhl(men)[Samson], that he may #underline[entertain] us.” So they called #myhl(men)[Samson] out of the prison, and he entertained them. They made him stand between the pillars. 
#versenum(26) And #myhl(men)[Samson] said to the young man who held him by the hand, “Let me #underline[feel] the pillars on which the house #underline[rests], that I may #underline[lean] against them.” 
#versenum(27) Now the house was full of men and women. All the lords of the #myhl(other)[Philistines] were there, and on the roof there were about #myhl(numbers)[3,000] men and women, who looked on while #myhl(men)[Samson] entertained.


  
#versenum(28) Then #myhl(men)[Samson] called to the #myhl(divine)[#smallcaps[Lord]] and said, “O #myhl(divine)[Lord] #myhl(divine)[GOD], please remember me and please strengthen me only this once, O #myhl(divine)[God], that I may be avenged on the #myhl(other)[Philistines] for my #myhl(numbers)[two] eyes.” 
#versenum(29) And #myhl(men)[Samson] #underline[grasped] the #myhl(numbers)[two] middle pillars on which the house rested, and he #underline[leaned] his weight against them, his right hand on the one and his left hand on the other. 
#versenum(30) And #myhl(men)[Samson] said, “Let me die with the #myhl(other)[Philistines].” Then he bowed with all his strength, and the house fell upon the lords and upon all the people who were in it. So the dead whom he killed at his death were more than those whom he had killed #underline[during] his life. 
#versenum(31) Then his brothers and all his family came down and took him and brought him up and buried him between #myhl(places)[Zorah] and #myhl(places)[Eshtaol] in the tomb of #myhl(men)[Manoah] his father. He had judged #myhl(people-groups)[Israel] #myhl(numbers)[twenty] years.


  
#chapter-heading[Judges 17]


#section-heading[Micah and the Levite]


#versenum(1) There was a man of #myhl(places)[the hill country of Ephraim], whose name was #myhl(men)[Micah]. 
#versenum(2) And he said to his mother, “The #myhl(numbers)[1,100] pieces of silver that were taken from you, about which you #underline[uttered] a curse, and also spoke it in my ears, behold, the silver is with me; I took it.” And his mother said, “Blessed be my son by the #myhl(divine)[#smallcaps[Lord]].” 
#versenum(3) And he restored the #myhl(numbers)[1,100] pieces of silver to his mother. And his mother said, “I #underline[dedicate] the silver to the #myhl(divine)[#smallcaps[Lord]] from my hand for my son, to make a carved image and a metal image. Now therefore I will restore it to you.” 
#versenum(4) So when he restored the money to his mother, his mother took #myhl(numbers)[200] pieces of silver and gave it to the #underline[silversmith], who made it into a carved image and a metal image. And it was in the house of #myhl(men)[Micah]. 
#versenum(5) And the man #myhl(men)[Micah] had a #underline[shrine], and he made an ephod and household gods, and ordained#footnote[Judges 17:5 Hebrew #emph[filled the hand of]; also verse 12] one of his sons, who became his priest. 
#versenum(6) In those days there was no king in #myhl(places)[Israel]. Everyone did what was right in his own eyes.


  
#versenum(7) Now there was a young man of #myhl(places)[Bethlehem in Judah], of the family of #myhl(other)[Judah], who was a #myhl(people-groups)[Levite], and he #underline[sojourned] there. 
#versenum(8) And the man departed from the town of #myhl(places)[Bethlehem in Judah] to sojourn where he could find a place. And as he journeyed, he came to #myhl(places)[the hill country of Ephraim] to the house of #myhl(men)[Micah]. 
#versenum(9) And #myhl(men)[Micah] said to him, “Where do you come from?” And he said to him, “I am a #myhl(people-groups)[Levite] of #myhl(places)[Bethlehem in Judah], and I am going to sojourn where I may find a place.” 
#versenum(10) And #myhl(men)[Micah] said to him, “Stay with me, and be to me a father and a priest, and I will give you #myhl(numbers)[ten] pieces of silver a year and a #underline[suit] of clothes and your living.” And the #myhl(people-groups)[Levite] went in. 
#versenum(11) And the #myhl(people-groups)[Levite] was content to dwell with the man, and the young man became to him like one of his sons. 
#versenum(12) And #myhl(men)[Micah] ordained the #myhl(people-groups)[Levite], and the young man became his priest, and was in the house of #myhl(men)[Micah]. 
#versenum(13) Then #myhl(men)[Micah] said, “Now I know that the #myhl(divine)[#smallcaps[Lord]] will #underline[prosper] me, because I have a #myhl(people-groups)[Levite] as priest.”


  
#chapter-heading[Judges 18]


#section-heading[Danites Take the Levite and the Idol]


#versenum(1) In those days there was no king in #myhl(places)[Israel]. And in those days the tribe of the people of #myhl(other)[Dan] was seeking for itself an inheritance to dwell in, for until then no inheritance among the tribes of #myhl(people-groups)[Israel] had fallen to them. 
#versenum(2) So the people of #myhl(other)[Dan] sent #myhl(numbers)[five] able men from the whole number of their tribe, from #myhl(places)[Zorah] and from #myhl(places)[Eshtaol], to spy out the land and to explore it. And they said to them, “Go and explore the land.” And they came to #myhl(places)[the hill country of Ephraim], to the house of #myhl(men)[Micah], and lodged there. 
#versenum(3) When they were by the house of #myhl(men)[Micah], they #underline[recognized] the voice of the young #myhl(people-groups)[Levite]. And they turned aside and said to him, “Who brought you here? What are you doing in this place? What is your business here?” 
#versenum(4) And he said to them, “This is how #myhl(men)[Micah] dealt with me: he has hired me, and I have become his priest.” 
#versenum(5) And they said to him, “#underline[Inquire] of #myhl(divine)[God], please, that we may know whether the journey on which we are #underline[setting] out will #underline[succeed].” 
#versenum(6) And the priest said to them, “Go in peace. The journey on which you go is under the #underline[eye] of the #myhl(divine)[#smallcaps[Lord]].”


  
#versenum(7) Then the #myhl(numbers)[five] men departed and came to #myhl(places)[Laish] and saw the people who were there, how they lived in #underline[security], after the manner of the #myhl(other)[Sidonians], quiet and unsuspecting, lacking#footnote[Judges 18:7 Compare 18:10; the meaning of the Hebrew word is uncertain] nothing that is in the earth and #underline[possessing] wealth, and how they were far from the #myhl(other)[Sidonians] and had no dealings with anyone. 
#versenum(8) And when they came to their brothers at #myhl(places)[Zorah] and #myhl(places)[Eshtaol], their brothers said to them, “What do you report?” 
#versenum(9) They said, “Arise, and let us go up against them, for we have seen the land, and behold, it is very good. And will you do nothing? Do not be #underline[slow] to go, to enter in and possess the land. 
#versenum(10) As soon as you go, you will come to an unsuspecting people. The land is #underline[spacious], for #myhl(divine)[God] has given it into your hands, a place where there is no lack of anything that is in the earth.”


  
#versenum(11) So #myhl(numbers)[600] men of the tribe of #myhl(other)[Dan], armed with weapons of war, set out from #myhl(places)[Zorah] and #myhl(places)[Eshtaol], 
#versenum(12) and went up and encamped at #myhl(places)[Kiriath-jearim] in #myhl(places)[Judah]. On this account that place is called #myhl(places)[Mahaneh-dan]#footnote[Judges 18:12 #emph[Mahaneh-dan] means #emph[camp of Dan]] to this day; behold, it is west of #myhl(places)[Kiriath-jearim]. 
#versenum(13) And they passed on from there to #myhl(places)[the hill country of Ephraim], and came to the house of #myhl(men)[Micah].


  
#versenum(14) Then the #myhl(numbers)[five] men who had gone to scout out the country of #myhl(places)[Laish] said to their brothers, “Do you know that in these houses there are an ephod, household gods, a carved image, and a metal image? Now therefore consider what you will do.” 
#versenum(15) And they turned aside there and came to the house of the young #myhl(people-groups)[Levite], at the home of #myhl(men)[Micah], and asked him about his #underline[welfare]. 
#versenum(16) Now the #myhl(numbers)[600] men of the #myhl(other)[Danites], armed with their weapons of war, stood by the entrance of the gate. 
#versenum(17) And the #myhl(numbers)[five] men who had gone to scout out the land went up and entered and took the carved image, the ephod, the household gods, and the metal image, while the priest stood by the entrance of the gate with the #myhl(numbers)[600] men armed with weapons of war. 
#versenum(18) And when these went into #myhl(men)[Micah]’s house and took the carved image, the ephod, the household gods, and the metal image, the priest said to them, “What are you doing?” 
#versenum(19) And they said to him, “Keep quiet; put your hand on your mouth and come with us and be to us a father and a priest. Is it better for you to be priest to the house of one man, or to be priest to a tribe and clan in #myhl(places)[Israel]?” 
#versenum(20) And the #underline[priest’s] heart was #underline[glad]. He took the ephod and the household gods and the carved image and went along with the people.


  
#versenum(21) So they turned and departed, putting the little ones and the livestock and the #underline[goods] in front of them. 
#versenum(22) When they had gone a distance from the home of #myhl(men)[Micah], the men who were in the houses near #myhl(men)[Micah]’s house were called out, and they overtook the people of #myhl(other)[Dan]. 
#versenum(23) And they shouted to the people of #myhl(other)[Dan], who turned around and said to #myhl(men)[Micah], “What is the matter with you, that you come with such a company?” 
#versenum(24) And he said, “You take my gods that I made and the priest, and go away, and what have I left? How then do you ask me, ‘What is the matter with you?’” 
#versenum(25) And the people of #myhl(other)[Dan] said to him, “Do not let your voice be heard among us, lest angry fellows fall upon you, and you #underline[lose] your life with the lives of your household.” 
#versenum(26) Then the people of #myhl(other)[Dan] went their way. And when #myhl(men)[Micah] saw that they were too strong for him, he turned and went back to his home.


  
#versenum(27) But the people of #myhl(other)[Dan] took what #myhl(men)[Micah] had made, and the priest who belonged to him, and they came to #myhl(places)[Laish], to a people quiet and unsuspecting, and struck them with the edge of the sword and burned the city with fire. 
#versenum(28) And there was no deliverer because it was far from #myhl(places)[Sidon], and they had no dealings with anyone. It was in the valley that belongs to #underline[#myhl(places)[Beth-rehob]]. Then they rebuilt the city and lived in it. 
#versenum(29) And they named the city #myhl(other)[Dan], after the name of #myhl(other)[Dan] their ancestor, who was born to #myhl(people-groups)[Israel]; but the name of the city was #myhl(places)[Laish] at the first. 
#versenum(30) And the people of #myhl(other)[Dan] set up the carved image for themselves, and #underline[#myhl(men)[Jonathan]] the son of #underline[#myhl(men)[Gershom]], son of #myhl(men)[Moses],#footnote[Judges 18:30 Or #emph[Manasseh]] and his sons were priests to the tribe of the #myhl(other)[Danites] until the day of the #underline[captivity] of the land. 
#versenum(31) So they set up #myhl(men)[Micah]’s carved image that he made, as long as the house of #myhl(divine)[God] was at #myhl(places)[Shiloh].


  
#chapter-heading[Judges 19]


#section-heading[A Levite and His Concubine]


#versenum(1) In those days, when there was no king in #myhl(places)[Israel], a certain #myhl(people-groups)[Levite] was sojourning in the remote parts of #myhl(places)[the hill country of Ephraim], who took to himself a concubine from #myhl(places)[Bethlehem in Judah]. 
#versenum(2) And his concubine was #underline[unfaithful] to#footnote[Judges 19:2 Septuagint, Old Latin #emph[became angry with]] him, and she went away from him to her father’s house at #myhl(places)[Bethlehem in Judah], and was there some #myhl(numbers)[four] months. 
#versenum(3) Then her husband arose and went after her, to speak kindly to her and bring her back. He had with him his servant and #myhl(numbers)[a couple] of donkeys. And she brought him into her father’s house. And when the girl’s father saw him, he came with #underline[joy] to meet him. 
#versenum(4) And his father-in-law, the girl’s father, made him stay, and he remained with him #myhl(numbers)[three] days. So they ate and drank and spent the night there. 
#versenum(5) And on the #myhl(numbers)[fourth] day they arose early in the morning, and he prepared to go, but the girl’s father said to his son-in-law, “Strengthen your heart with a morsel of bread, and after that you may go.” 
#versenum(6) So the #myhl(numbers)[two] of them sat and ate and drank together. And the girl’s father said to the man, “Be #underline[pleased] to spend the night, and let your heart be merry.” 
#versenum(7) And when the man rose up to go, his father-in-law pressed him, till he spent the night there again. 
#versenum(8) And on the #myhl(numbers)[fifth] day he arose early in the morning to depart. And the girl’s father said, “Strengthen your heart and wait until the day #underline[declines].” So they ate, both of them. 
#versenum(9) And when the man and his concubine and his servant rose up to depart, his father-in-law, the girl’s father, said to him, “Behold, now the day has #underline[waned] toward evening. Please, spend the night. Behold, the day #underline[draws] to its close. Lodge here and let your heart be merry, and tomorrow you shall arise early in the morning for your journey, and go home.”


  
#versenum(10) But the man would not spend the night. He rose up and departed and arrived opposite #myhl(places)[Jebus] (that is, #myhl(places)[Jerusalem]). He had with him #myhl(numbers)[a couple] of #underline[saddled] donkeys, and his concubine was with him. 
#versenum(11) When they were near #myhl(places)[Jebus], the day was #underline[nearly] over, and the servant said to his master, “Come now, let us turn aside to this city of the #myhl(other)[Jebusites] and spend the night in it.” 
#versenum(12) And his master said to him, “We will not turn aside into the city of #underline[foreigners], who do not belong to the #myhl(people-groups)[people of Israel], but we will pass on to #myhl(places)[Gibeah].” 
#versenum(13) And he said to his young man, “Come and let us draw near to one of these places and spend the night at #myhl(places)[Gibeah] or at #myhl(places)[Ramah].” 
#versenum(14) So they passed on and went their way. And the sun went down on them near #myhl(places)[Gibeah], which belongs to #myhl(men)[Benjamin], 
#versenum(15) and they turned aside there, to go in and spend the night at #myhl(places)[Gibeah]. And he went in and sat down in the open square of the city, for no one took them into his house to spend the night.


  
#versenum(16) And behold, an old man was coming from his work in the field at evening. The man was from #myhl(places)[the hill country of Ephraim], and he was sojourning in #myhl(places)[Gibeah]. The men of the place were #myhl(other)[Benjaminites]. 
#versenum(17) And he lifted up his eyes and saw the #underline[traveler] in the open square of the city. And the old man said, “Where are you going? And where do you come from?” 
#versenum(18) And he said to him, “We are passing from #myhl(places)[Bethlehem in Judah] to the remote parts of #myhl(places)[the hill country of Ephraim], from which I come. I went to #myhl(places)[Bethlehem in Judah], and I am going to the house of the #myhl(divine)[#smallcaps[Lord]],#footnote[Judges 19:18 Septuagint #emph[my home]; compare verse 29] but no one has taken me into his house. 
#versenum(19) We have #underline[straw] and feed for our donkeys, with bread and wine for me and your female servant and the young man with your servants. There is no lack of anything.” 
#versenum(20) And the old man said, “Peace be to you; I will care for all your #underline[wants]. Only, do not spend the night in the square.” 
#versenum(21) So he brought him into his house and gave the donkeys feed. And they #underline[washed] their feet, and ate and drank.


  
#section-heading[Gibeah’s Crime]


#versenum(22) As they were making their hearts merry, behold, the men of the city, worthless fellows, surrounded the house, beating on the door. And they said to the old man, the master of the house, “Bring out the man who came into your house, that we may know him.” 
#versenum(23) And the man, the master of the house, went out to them and said to them, “No, my brothers, do not act so #underline[wickedly]; since this man has come into my house, do not do this #underline[vile] thing. 
#versenum(24) Behold, here are my #underline[virgin] daughter and his concubine. Let me bring them out now. #underline[Violate] them and do with them what seems good to you, but against this man do not do this outrageous thing.” 
#versenum(25) But the men would not listen to him. So the man seized his concubine and made her go out to them. And they knew her and #underline[abused] her all night until the morning. And as the dawn began to break, they let her go. 
#versenum(26) And as morning appeared, the woman came and fell down at the door of the man’s house where her master was, until it was light.


  
#versenum(27) And her master rose up in the morning, and when he opened the doors of the house and went out to go on his way, behold, there was his concubine lying at the door of the house, with her hands on the #underline[threshold]. 
#versenum(28) He said to her, “Get up, let us be going.” But there was no answer. Then he put her on the donkey, and the man rose up and went away to his home. 
#versenum(29) And when he entered his house, he took a #underline[knife], and taking hold of his concubine he divided her, limb by limb, into #myhl(numbers)[twelve] pieces, and sent her throughout all the territory of #myhl(places)[Israel]. 
#versenum(30) And all who saw it said, “Such a thing has never happened or been seen from the day that the #myhl(people-groups)[people of Israel] came up out of the land of #myhl(places)[Egypt] until this day; consider it, take counsel, and speak.”


  
#chapter-heading[Judges 20]


#section-heading[Israel’s War with the Tribe of Benjamin]


#versenum(1) Then all the #myhl(people-groups)[people of Israel] came out, from #myhl(places)[Dan] to #myhl(places)[Beersheba], #underline[including] the land of #myhl(places)[Gilead], and the congregation assembled as one man to the #myhl(divine)[#smallcaps[Lord]] at #myhl(places)[Mizpah]. 
#versenum(2) And the chiefs of all the people, of all the tribes of #myhl(people-groups)[Israel], presented themselves in the assembly of the people of #myhl(divine)[God], #myhl(numbers)[400,000] men on foot that drew the sword. 
#versenum(3) (Now the people of #myhl(men)[Benjamin] heard that the #myhl(people-groups)[people of Israel] had gone up to #myhl(places)[Mizpah].) And the #myhl(people-groups)[people of Israel] said, “Tell us, how did this evil #underline[happen]?” 
#versenum(4) And the #myhl(people-groups)[Levite], the husband of the woman who was #underline[murdered], answered and said, “I came to #myhl(places)[Gibeah] that belongs to #myhl(men)[Benjamin], I and my concubine, to spend the night. 
#versenum(5) And the leaders of #myhl(places)[Gibeah] rose against me and surrounded the house against me by night. They meant to kill me, and they #underline[violated] my concubine, and she is dead. 
#versenum(6) So I took hold of my concubine and cut her in pieces and sent her throughout all the country of the inheritance of #myhl(people-groups)[Israel], for they have committed #underline[abomination] and outrage in #myhl(places)[Israel]. 
#versenum(7) Behold, you #myhl(people-groups)[people of Israel], all of you, give your #underline[advice] and counsel here.”


  
#versenum(8) And all the people arose as one man, saying, “None of us will go to his tent, and none of us will return to his house. 
#versenum(9) But now this is what we will do to #myhl(places)[Gibeah]: we will go up against it by lot, 
#versenum(10) and we will take #myhl(numbers)[ten] men of a #myhl(numbers)[hundred] throughout all the tribes of #myhl(people-groups)[Israel], and a #myhl(numbers)[hundred] of a #myhl(numbers)[thousand], and a #myhl(numbers)[thousand of ten thousand], to bring provisions for the people, that when they come they may repay #myhl(places)[Gibeah] of #myhl(men)[Benjamin] for all the outrage that they have committed in #myhl(places)[Israel].” 
#versenum(11) So all the #myhl(people-groups)[men of Israel] gathered against the city, #underline[united] as one man.


  
#versenum(12) And the tribes of #myhl(people-groups)[Israel] sent men through all the tribe of #myhl(men)[Benjamin], saying, “What evil is this that has taken place among you? 
#versenum(13) Now therefore give up the men, the worthless fellows in #myhl(places)[Gibeah], that we may put them to death and #underline[purge] evil from #myhl(people-groups)[Israel].” But the #myhl(other)[Benjaminites] would not listen to the voice of their brothers, the #myhl(people-groups)[people of Israel]. 
#versenum(14) Then the people of #myhl(men)[Benjamin] came together out of the cities to #myhl(places)[Gibeah] to go out to battle against the #myhl(people-groups)[people of Israel]. 
#versenum(15) And the people of #myhl(men)[Benjamin] mustered out of their cities on that day #underline[#myhl(numbers)[26,000]] men who drew the sword, besides the inhabitants of #myhl(places)[Gibeah], who mustered #myhl(numbers)[700] chosen men. 
#versenum(16) Among all these were #myhl(numbers)[700] chosen men who were left-handed; every one could #underline[sling] a stone at a hair and not #underline[miss]. 
#versenum(17) And the #myhl(people-groups)[men of Israel], apart from #myhl(men)[Benjamin], mustered #myhl(numbers)[400,000] men who drew the sword; all these were men of war.


  
#versenum(18) The #myhl(people-groups)[people of Israel] arose and went up to #myhl(places)[Bethel] and inquired of #myhl(divine)[God], “Who shall go up #myhl(numbers)[first] for us to fight against the people of #myhl(men)[Benjamin]?” And the #myhl(divine)[#smallcaps[Lord]] said, “#myhl(other)[Judah] shall go up #myhl(numbers)[first].”


  
#versenum(19) Then the #myhl(people-groups)[people of Israel] rose in the morning and encamped against #myhl(places)[Gibeah]. 
#versenum(20) And the #myhl(people-groups)[men of Israel] went out to fight against #myhl(men)[Benjamin], and the #myhl(people-groups)[men of Israel] drew up the battle line against them at #myhl(places)[Gibeah]. 
#versenum(21) The people of #myhl(men)[Benjamin] came out of #myhl(places)[Gibeah] and destroyed on that day #myhl(numbers)[22,000] men of the #myhl(people-groups)[Israelites]. 
#versenum(22) But the people, the #myhl(people-groups)[men of Israel], took #underline[courage], and again formed the battle line in the same place where they had formed it on the #myhl(numbers)[first] day. 
#versenum(23) And the #myhl(people-groups)[people of Israel] went up and wept before the #myhl(divine)[#smallcaps[Lord]] until the evening. And they inquired of the #myhl(divine)[#smallcaps[Lord]], “Shall we again draw near to fight against our brothers, the people of #myhl(men)[Benjamin]?” And the #myhl(divine)[#smallcaps[Lord]] said, “Go up against them.”


  
#versenum(24) So the #myhl(people-groups)[people of Israel] came near against the people of #myhl(men)[Benjamin] the #myhl(numbers)[second] day. 
#versenum(25) And #myhl(men)[Benjamin] went against them out of #myhl(places)[Gibeah] the #myhl(numbers)[second] day, and destroyed #underline[#myhl(numbers)[18,000]] men of the #myhl(people-groups)[people of Israel]. All these were men who drew the sword. 
#versenum(26) Then all the #myhl(people-groups)[people of Israel], the whole army, went up and came to #myhl(places)[Bethel] and wept. They sat there before the #myhl(divine)[#smallcaps[Lord]] and #underline[fasted] that day until evening, and offered burnt offerings and peace offerings before the #myhl(divine)[#smallcaps[Lord]]. 
#versenum(27) And the #myhl(people-groups)[people of Israel] inquired of the #myhl(divine)[#smallcaps[Lord]] (for the ark of the covenant of #myhl(divine)[God] was there in those days, 
#versenum(28) and #myhl(men)[Phinehas] the son of #myhl(men)[Eleazar], son of #myhl(men)[Aaron], #underline[ministered] before it in those days), saying, “Shall we go out once more to battle against our brothers, the people of #myhl(men)[Benjamin], or shall we cease?” And the #myhl(divine)[#smallcaps[Lord]] said, “Go up, for tomorrow I will give them into your hand.”


  
#versenum(29) So #myhl(people-groups)[Israel] set men in ambush around #myhl(places)[Gibeah]. 
#versenum(30) And the #myhl(people-groups)[people of Israel] went up against the people of #myhl(men)[Benjamin] on the #myhl(numbers)[third] day and set themselves in array against #myhl(places)[Gibeah], as at other times. 
#versenum(31) And the people of #myhl(men)[Benjamin] went out against the people and were drawn away from the city. And as at other times they began to strike and kill some of the people in the highways, one of which goes up to #myhl(places)[Bethel] and the other to #myhl(places)[Gibeah], and in the open country, about #myhl(numbers)[thirty] #myhl(people-groups)[men of Israel]. 
#versenum(32) And the people of #myhl(men)[Benjamin] said, “They are routed before us, as at the first.” But the #myhl(people-groups)[people of Israel] said, “Let us flee and draw them away from the city to the highways.” 
#versenum(33) And all the #myhl(people-groups)[men of Israel] rose up out of their place and set themselves in array at #underline[#myhl(other)[Baal-tamar]], and the #myhl(people-groups)[men of Israel] who were in ambush rushed out of their place from #underline[#myhl(places)[Maareh-geba]].#footnote[Judges 20:33 Some Septuagint manuscripts #emph[place west of Geba]] 
#versenum(34) And there came against #myhl(places)[Gibeah] #myhl(numbers)[10,000] chosen men out of all #myhl(people-groups)[Israel], and the battle was hard, but the #myhl(other)[Benjaminites] did not know that disaster was close upon them. 
#versenum(35) And the #myhl(divine)[#smallcaps[Lord]] defeated #myhl(men)[Benjamin] before #myhl(people-groups)[Israel], and the #myhl(people-groups)[people of Israel] destroyed #underline[#myhl(numbers)[25,100]] men of #myhl(men)[Benjamin] that day. All these were men who drew the sword. 
#versenum(36) So the people of #myhl(men)[Benjamin] saw that they were defeated.


  The #myhl(people-groups)[men of Israel] gave ground to #myhl(men)[Benjamin], because they #underline[trusted] the men in ambush whom they had set against #myhl(places)[Gibeah]. 
#versenum(37) Then the men in ambush hurried and rushed against #myhl(places)[Gibeah]; the men in ambush moved out and struck all the city with the edge of the sword. 
#versenum(38) Now the appointed signal between the #myhl(people-groups)[men of Israel] and the men in the main ambush was that when they made a great #underline[cloud] of smoke rise up out of the city 
#versenum(39) the #myhl(people-groups)[men of Israel] should turn in battle. Now #myhl(men)[Benjamin] had #underline[begun] to strike and kill about #myhl(numbers)[thirty] #myhl(people-groups)[men of Israel]. They said, “Surely they are defeated before us, as in the #myhl(numbers)[first] battle.” 
#versenum(40) But when the signal began to rise out of the city in a #underline[column] of smoke, the #myhl(other)[Benjaminites] looked behind them, and behold, the whole of the city went up in smoke to heaven. 
#versenum(41) Then the #myhl(people-groups)[men of Israel] turned, and the men of #myhl(men)[Benjamin] were dismayed, for they saw that disaster was close upon them. 
#versenum(42) Therefore they turned their backs before the #myhl(people-groups)[men of Israel] in the direction of the wilderness, but the battle overtook them. And those who came out of the cities were #underline[destroying] them in their midst. 
#versenum(43) Surrounding the #myhl(other)[Benjaminites], they pursued them and trod them down from #underline[#myhl(places)[Nohah]]#footnote[Judges 20:43 Septuagint; Hebrew \[at their\] #emph[resting place]] as far as opposite #myhl(places)[Gibeah] on the east. 
#versenum(44) #myhl(numbers)[Eighteen thousand] men of #myhl(men)[Benjamin] fell, all of them men of valor. 
#versenum(45) And they turned and fled toward the wilderness to the #myhl(places)[rock of Rimmon]. #myhl(numbers)[Five thousand] men of them were cut down in the highways. And they were pursued hard to #underline[#myhl(places)[Gidom]], and #myhl(numbers)[2,000] men of them were struck down. 
#versenum(46) So all who fell that day of #myhl(men)[Benjamin] were #underline[#myhl(numbers)[25,000]] men who drew the sword, all of them men of valor. 
#versenum(47) But #myhl(numbers)[600] men turned and fled toward the wilderness to the #myhl(places)[rock of Rimmon] and remained at the #myhl(places)[rock of Rimmon] #myhl(numbers)[four] months. 
#versenum(48) And the #myhl(people-groups)[men of Israel] turned back against the people of #myhl(men)[Benjamin] and struck them with the edge of the sword, the city, men and #underline[beasts] and all that they found. And all the towns that they found they set on fire.


  
#chapter-heading[Judges 21]


#section-heading[Wives Provided for the Tribe of Benjamin]


#versenum(1) Now the #myhl(people-groups)[men of Israel] had sworn at #myhl(places)[Mizpah], “No one of us shall give his daughter in marriage to #myhl(men)[Benjamin].” 
#versenum(2) And the people came to #myhl(places)[Bethel] and sat there till evening before #myhl(divine)[God], and they lifted up their voices and wept bitterly. 
#versenum(3) And they said, “O #myhl(divine)[#smallcaps[Lord], the God of Israel], why has this happened in #myhl(places)[Israel], that today there should be one tribe lacking in #myhl(places)[Israel]?” 
#versenum(4) And the next day the people rose early and built there an altar and offered burnt offerings and peace offerings. 
#versenum(5) And the #myhl(people-groups)[people of Israel] said, “Which of all the tribes of #myhl(people-groups)[Israel] did not come up in the assembly to the #myhl(divine)[#smallcaps[Lord]]?” For they had taken a great oath concerning him who did not come up to the #myhl(divine)[#smallcaps[Lord]] to #myhl(places)[Mizpah], saying, “He shall surely be put to death.” 
#versenum(6) And the #myhl(people-groups)[people of Israel] had compassion for #myhl(men)[Benjamin] their brother and said, “One tribe is cut off from #myhl(people-groups)[Israel] this day. 
#versenum(7) What shall we do for wives for those who are left, since we have sworn by the #myhl(divine)[#smallcaps[Lord]] that we will not give them any of our daughters for wives?”


  
#versenum(8) And they said, “What one is there of the tribes of #myhl(people-groups)[Israel] that did not come up to the #myhl(divine)[#smallcaps[Lord]] to #myhl(places)[Mizpah]?” And behold, no one had come to the camp from #myhl(places)[Jabesh-gilead], to the assembly. 
#versenum(9) For when the people were mustered, behold, not one of the inhabitants of #myhl(places)[Jabesh-gilead] was there. 
#versenum(10) So the congregation sent #myhl(numbers)[12,000] of their #underline[bravest] men there and commanded them, “Go and strike the inhabitants of #myhl(places)[Jabesh-gilead] with the edge of the sword; also the women and the little ones. 
#versenum(11) This is what you shall do: every male and every woman that has #underline[lain] with a male you shall #underline[devote] to destruction.” 
#versenum(12) And they found among the inhabitants of #myhl(places)[Jabesh-gilead] #underline[#myhl(numbers)[400]] young #underline[virgins] who had not known a man by lying with him, and they brought them to the camp at #myhl(places)[Shiloh], which is in the land of #myhl(places)[Canaan].


  
#versenum(13) Then the whole congregation sent word to the people of #myhl(men)[Benjamin] who were at the #myhl(places)[rock of Rimmon] and #underline[proclaimed] peace to them. 
#versenum(14) And #myhl(men)[Benjamin] returned at that time. And they gave them the women whom they had saved alive of the women of #myhl(places)[Jabesh-gilead], but they were not enough for them. 
#versenum(15) And the people had compassion on #myhl(men)[Benjamin] because the #myhl(divine)[#smallcaps[Lord]] had made a breach in the tribes of #myhl(people-groups)[Israel].


  
#versenum(16) Then the elders of the congregation said, “What shall we do for wives for those who are left, since the women are destroyed out of #myhl(men)[Benjamin]?” 
#versenum(17) And they said, “There must be an inheritance for the #underline[survivors] of #myhl(men)[Benjamin], that a tribe not be #underline[blotted] out from #myhl(people-groups)[Israel]. 
#versenum(18) Yet we cannot give them wives from our daughters.” For the #myhl(people-groups)[people of Israel] had sworn, “Cursed be he who gives a wife to #myhl(men)[Benjamin].” 
#versenum(19) So they said, “Behold, there is the #underline[yearly] feast of the #myhl(divine)[#smallcaps[Lord]] at #myhl(places)[Shiloh], which is north of #myhl(places)[Bethel], on the east of the #underline[highway] that goes up from #myhl(places)[Bethel] to #myhl(places)[Shechem], and south of #underline[#myhl(places)[Lebonah]].” 
#versenum(20) And they commanded the people of #myhl(men)[Benjamin], saying, “Go and lie in ambush in the vineyards 
#versenum(21) and watch. If the daughters of #myhl(places)[Shiloh] come out to #underline[dance] in the dances, then come out of the vineyards and #underline[snatch] each man his wife from the daughters of #myhl(places)[Shiloh], and go to the land of #myhl(men)[Benjamin]. 
#versenum(22) And when their fathers or their brothers come to #underline[complain] to us, we will say to them, ‘Grant them #underline[graciously] to us, because we did not take for each man of them his wife in battle, neither did you give them to them, #underline[else] you would now be #underline[guilty].’” 
#versenum(23) And the people of #myhl(men)[Benjamin] did so and took their wives, according to their number, from the #underline[dancers] whom they carried off. Then they went and returned to their inheritance and rebuilt the towns and lived in them. 
#versenum(24) And the #myhl(people-groups)[people of Israel] departed from there at that time, every man to his tribe and family, and they went out from there every man to his inheritance.


  
#versenum(25) In those days there was no king in #myhl(places)[Israel]. Everyone did what was right in his own eyes.


  
#chapter-heading[Ruth 1]


#section-heading[Naomi Widowed]


#versenum(1) In the days when the judges ruled there was a #underline[famine] in the land, and a man of #myhl(places)[Bethlehem in Judah] went to sojourn in the country of #myhl(places)[Moab], he and his wife and his #myhl(numbers)[two] sons. 
#versenum(2) The name of the man was #myhl(men)[Elimelech] and the name of his wife #myhl(women)[Naomi], and the names of his #myhl(numbers)[two] sons were #myhl(men)[Mahlon] and #myhl(men)[Chilion]. They were #underline[#myhl(other)[Ephrathites]] from #myhl(places)[Bethlehem in Judah]. They went into the country of #myhl(places)[Moab] and remained there. 
#versenum(3) But #myhl(men)[Elimelech], the husband of #myhl(women)[Naomi], died, and she was left with her #myhl(numbers)[two] sons. 
#versenum(4) These took #myhl(other)[Moabite] wives; the name of the one was #myhl(women)[Orpah] and the name of the other #myhl(women)[Ruth]. They lived there about #myhl(numbers)[ten] years, 
#versenum(5) and both #myhl(men)[Mahlon] and #myhl(men)[Chilion] died, so that the woman was left without her #myhl(numbers)[two] sons and her husband.


  
#section-heading[Ruth’s Loyalty to Naomi]


#versenum(6) Then she arose with her daughters-in-law to return from the country of #myhl(places)[Moab], for she had heard in the fields of #myhl(places)[Moab] that the #myhl(divine)[#smallcaps[Lord]] had #underline[visited] his people and given them food. 
#versenum(7) So she set out from the place where she was with her #myhl(numbers)[two] daughters-in-law, and they went on the way to return to the land of #myhl(other)[Judah]. 
#versenum(8) But #myhl(women)[Naomi] said to her #myhl(numbers)[two] daughters-in-law, “Go, return each of you to her mother’s house. May the #myhl(divine)[#smallcaps[Lord]] deal kindly with you, as you have dealt with the dead and with me. 
#versenum(9) The #myhl(divine)[#smallcaps[Lord]] grant that you may find rest, each of you in the house of her husband!” Then she kissed them, and they lifted up their voices and wept. 
#versenum(10) And they said to her, “No, we will return with you to your people.” 
#versenum(11) But #myhl(women)[Naomi] said, “Turn back, my daughters; why will you go with me? Have I yet sons in my womb that they may become your #underline[husbands]? 
#versenum(12) Turn back, my daughters; go your way, for I am too old to have a husband. If I should say I have #underline[hope], even if I should have a husband this night and should bear sons, 
#versenum(13) would you therefore wait till they were #underline[grown]? Would you therefore #underline[refrain] from #underline[marrying]? No, my daughters, for it is #underline[exceedingly] #underline[bitter] to me for your sake that the hand of the #myhl(divine)[#smallcaps[Lord]] has gone out against me.” 
#versenum(14) Then they lifted up their voices and wept again. And #myhl(women)[Orpah] kissed her mother-in-law, but #myhl(women)[Ruth] #underline[clung] to her.


  
#versenum(15) And she said, “See, your sister-in-law has gone back to her people and to her gods; return after your sister-in-law.” 
#versenum(16) But #myhl(women)[Ruth] said, “Do not #underline[urge] me to leave you or to return from following you. For where you go I will go, and where you lodge I will lodge. Your people shall be my people, and your #myhl(divine)[God] my #myhl(divine)[God]. 
#versenum(17) Where you die I will die, and there will I be buried. May the #myhl(divine)[#smallcaps[Lord]] do so to me and more also if anything but death parts me from you.” 
#versenum(18) And when #myhl(women)[Naomi] saw that she was #underline[determined] to go with her, she said no more.


  
#section-heading[Naomi and Ruth Return]


#versenum(19) So the #myhl(numbers)[two] of them went on until they came to #myhl(places)[Bethlehem]. And when they came to #myhl(places)[Bethlehem], the whole town was #underline[stirred] because of them. And the women said, “Is this #myhl(women)[Naomi]?” 
#versenum(20) She said to them, “Do not call me #myhl(women)[Naomi];#footnote[Ruth 1:20 #emph[Naomi] means #emph[pleasant]] call me #underline[Mara],#footnote[Ruth 1:20 #emph[Mara] means #emph[bitter]] for the Almighty has dealt very bitterly with me. 
#versenum(21) I went away full, and the #myhl(divine)[#smallcaps[Lord]] has brought me back empty. Why call me #myhl(women)[Naomi], when the #myhl(divine)[#smallcaps[Lord]] has #underline[testified] against me and the Almighty has brought #underline[calamity] upon me?”


  
#versenum(22) So #myhl(women)[Naomi] returned, and #myhl(women)[Ruth] the #myhl(other)[Moabite] her daughter-in-law with her, who returned from the country of #myhl(places)[Moab]. And they came to #myhl(places)[Bethlehem] at the beginning of barley harvest.


  
#chapter-heading[Ruth 2]


#section-heading[Ruth Meets Boaz]


#versenum(1) Now #myhl(women)[Naomi] had a relative of her #underline[husband’s], a worthy man of the clan of #myhl(men)[Elimelech], whose name was #myhl(men)[Boaz]. 
#versenum(2) And #myhl(women)[Ruth] the #myhl(other)[Moabite] said to #myhl(women)[Naomi], “Let me go to the field and glean among the ears of grain after him in whose sight I shall find favor.” And she said to her, “Go, my daughter.” 
#versenum(3) So she set out and went and gleaned in the field after the reapers, and she happened to come to the part of the field belonging to #myhl(men)[Boaz], who was of the clan of #myhl(men)[Elimelech]. 
#versenum(4) And behold, #myhl(men)[Boaz] came from #myhl(places)[Bethlehem]. And he said to the reapers, “The #myhl(divine)[#smallcaps[Lord]] be with you!” And they answered, “The #myhl(divine)[#smallcaps[Lord]] bless you.” 
#versenum(5) Then #myhl(men)[Boaz] said to his young man who was in charge of the reapers, “Whose young woman is this?” 
#versenum(6) And the servant who was in charge of the reapers answered, “She is the young #myhl(other)[Moabite] woman, who came back with #myhl(women)[Naomi] from the country of #myhl(places)[Moab]. 
#versenum(7) She said, ‘Please let me glean and gather among the sheaves after the reapers.’ So she came, and she has #underline[continued] from early morning until now, except for a #underline[short] rest.”#footnote[Ruth 2:7 Compare Septuagint, Vulgate; the meaning of the Hebrew phrase is uncertain]


  
#versenum(8) Then #myhl(men)[Boaz] said to #myhl(women)[Ruth], “Now, listen, my daughter, do not go to glean in another field or leave this one, but keep close to my young women. 
#versenum(9) Let your eyes be on the field that they are #underline[reaping], and go after them. Have I not charged the young men not to touch you? And when you are thirsty, go to the vessels and drink what the young men have drawn.” 
#versenum(10) Then she fell on her face, bowing to the ground, and said to him, “Why have I found favor in your eyes, that you should take notice of me, since I am a #underline[foreigner]?” 
#versenum(11) But #myhl(men)[Boaz] answered her, “All that you have done for your mother-in-law since the death of your husband has been #underline[fully] told to me, and how you left your father and mother and your native land and came to a people that you did not know before. 
#versenum(12) The #myhl(divine)[#smallcaps[Lord]] repay you for what you have done, and a full #underline[reward] be given you by the #myhl(divine)[#smallcaps[Lord], the God of Israel], under whose wings you have come to take refuge!” 
#versenum(13) Then she said, “I have found favor in your eyes, my lord, for you have #underline[comforted] me and spoken kindly to your servant, though I am not one of your servants.”


  
#versenum(14) And at #underline[mealtime] #myhl(men)[Boaz] said to her, “Come here and eat some bread and #underline[dip] your morsel in the wine.” So she sat beside the reapers, and he passed to her #underline[roasted] grain. And she ate until she was satisfied, and she had some left over. 
#versenum(15) When she rose to glean, #myhl(men)[Boaz] #underline[instructed] his young men, saying, “Let her glean even among the sheaves, and do not reproach her. 
#versenum(16) And also pull out some from the #underline[bundles] for her and leave it for her to glean, and do not #underline[rebuke] her.”


  
#versenum(17) So she gleaned in the field until evening. Then she beat out what she had gleaned, and it was about an ephah#footnote[Ruth 2:17 An #emph[ephah] was about 3/5 bushel or 22 liters] of barley. 
#versenum(18) And she took it up and went into the city. Her mother-in-law saw what she had gleaned. She also brought out and gave her what food she had left over after being satisfied. 
#versenum(19) And her mother-in-law said to her, “Where did you glean today? And where have you worked? Blessed be the man who took notice of you.” So she told her mother-in-law with whom she had worked and said, “The man’s name with whom I worked today is #myhl(men)[Boaz].” 
#versenum(20) And #myhl(women)[Naomi] said to her daughter-in-law, “May he be blessed by the #myhl(divine)[#smallcaps[Lord]], whose kindness has not forsaken the living or the dead!” #myhl(women)[Naomi] also said to her, “The man is a close relative of ours, one of our #underline[redeemers].” 
#versenum(21) And #myhl(women)[Ruth] the #myhl(other)[Moabite] said, “Besides, he said to me, ‘You shall keep close by my young men until they have finished all my harvest.’” 
#versenum(22) And #myhl(women)[Naomi] said to #myhl(women)[Ruth], her daughter-in-law, “It is good, my daughter, that you go out with his young women, lest in another field you be #underline[assaulted].” 
#versenum(23) So she kept close to the young women of #myhl(men)[Boaz], gleaning until the end of the barley and wheat #underline[harvests]. And she lived with her mother-in-law.


  
#chapter-heading[Ruth 3]


#section-heading[Ruth and Boaz at the Threshing Floor]


#versenum(1) Then #myhl(women)[Naomi] her mother-in-law said to her, “My daughter, should I not #underline[seek] rest for you, that it may be well with you? 
#versenum(2) Is not #myhl(men)[Boaz] our relative, with whose young women you were? See, he is #underline[winnowing] barley tonight at the threshing floor. 
#versenum(3) #underline[Wash] therefore and anoint yourself, and put on your cloak and go down to the threshing floor, but do not make yourself known to the man until he has finished eating and #underline[drinking]. 
#versenum(4) But when he lies down, observe the place where he lies. Then go and #underline[uncover] his feet and lie down, and he will tell you what to do.” 
#versenum(5) And she replied, “All that you say I will do.”


  
#versenum(6) So she went down to the threshing floor and did just as her mother-in-law had commanded her. 
#versenum(7) And when #myhl(men)[Boaz] had #underline[eaten] and #underline[drunk], and his heart was merry, he went to lie down at the end of the heap of grain. Then she came softly and #underline[uncovered] his feet and lay down. 
#versenum(8) At midnight the man was #underline[startled] and turned over, and behold, a woman lay at his feet! 
#versenum(9) He said, “Who are you?” And she answered, “I am #myhl(women)[Ruth], your servant. Spread your wings#footnote[Ruth 3:9 Compare 2:12; the word for #emph[wings] can also mean #emph[corners of a garment]] over your servant, for you are a redeemer.” 
#versenum(10) And he said, “May you be blessed by the #myhl(divine)[#smallcaps[Lord]], my daughter. You have made this last kindness greater than the #myhl(numbers)[first] in that you have not gone after young men, whether #underline[poor] or rich. 
#versenum(11) And now, my daughter, do not fear. I will do for you all that you ask, for all my #underline[fellow] #underline[townsmen] know that you are a worthy woman. 
#versenum(12) And now it is true that I am a redeemer. Yet there is a redeemer #underline[nearer] than I. 
#versenum(13) Remain tonight, and in the morning, if he will redeem you, good; let him do it. But if he is not #underline[willing] to redeem you, then, as the #myhl(divine)[#smallcaps[Lord]] lives, I will redeem you. Lie down until the morning.”


  
#versenum(14) So she lay at his feet until the morning, but arose before one could #underline[recognize] another. And he said, “Let it not be known that the woman came to the threshing floor.” 
#versenum(15) And he said, “Bring the #underline[garment] you are #underline[wearing] and hold it out.” So she held it, and he #underline[measured] out #myhl(numbers)[six] measures of barley and put it on her. Then she went into the city. 
#versenum(16) And when she came to her mother-in-law, she said, “How did you #underline[fare], my daughter?” Then she told her all that the man had done for her, 
#versenum(17) saying, “These #myhl(numbers)[six] measures of barley he gave to me, for he said to me, ‘You must not go back #underline[empty-handed] to your mother-in-law.’” 
#versenum(18) She replied, “Wait, my daughter, until you #underline[learn] how the matter turns out, for the man will not rest but will #underline[settle] the matter today.”


  
#chapter-heading[Ruth 4]


#section-heading[Boaz Redeems Ruth]


#versenum(1) Now #myhl(men)[Boaz] had gone up to the gate and sat down there. And behold, the redeemer, of whom #myhl(men)[Boaz] had spoken, came by. So #myhl(men)[Boaz] said, “Turn aside, #underline[friend]; sit down here.” And he turned aside and sat down. 
#versenum(2) And he took #myhl(numbers)[ten] men of the elders of the city and said, “Sit down here.” So they sat down. 
#versenum(3) Then he said to the redeemer, “#myhl(women)[Naomi], who has come back from the country of #myhl(places)[Moab], is #underline[selling] the #underline[parcel] of land that belonged to our relative #myhl(men)[Elimelech]. 
#versenum(4) So I thought I would tell you of it and say, ‘Buy it in the presence of those sitting here and in the presence of the elders of my people.’ If you will redeem it, redeem it. But if you#footnote[Ruth 4:4 Hebrew #emph[he]] will not, tell me, that I may know, for there is no one besides you to redeem it, and I come after you.” And he said, “I will redeem it.” 
#versenum(5) Then #myhl(men)[Boaz] said, “The day you buy the field from the hand of #myhl(women)[Naomi], you also #underline[acquire] #myhl(women)[Ruth]#footnote[Ruth 4:5 Masoretic Text #emph[you also buy it from Ruth]] the #myhl(other)[Moabite], the widow of the dead, in order to perpetuate the name of the dead in his inheritance.” 
#versenum(6) Then the redeemer said, “I cannot redeem it for myself, lest I #underline[impair] my own inheritance. Take my right of #underline[redemption] yourself, for I cannot redeem it.”


  
#versenum(7) Now this was the custom in #underline[former] times in #myhl(places)[Israel] concerning #underline[redeeming] and #underline[exchanging]: to #underline[confirm] a #underline[transaction], the one drew off his sandal and gave it to the other, and this was the manner of #underline[attesting] in #myhl(places)[Israel]. 
#versenum(8) So when the redeemer said to #myhl(men)[Boaz], “Buy it for yourself,” he drew off his sandal. 
#versenum(9) Then #myhl(men)[Boaz] said to the elders and all the people, “You are witnesses this day that I have bought from the hand of #myhl(women)[Naomi] all that belonged to #myhl(men)[Elimelech] and all that belonged to #myhl(men)[Chilion] and to #myhl(men)[Mahlon]. 
#versenum(10) Also #myhl(women)[Ruth] the #myhl(other)[Moabite], the widow of #myhl(men)[Mahlon], I have bought to be my wife, to perpetuate the name of the dead in his inheritance, that the name of the dead may not be cut off from among his brothers and from the gate of his native place. You are witnesses this day.” 
#versenum(11) Then all the people who were at the gate and the elders said, “We are witnesses. May the #myhl(divine)[#smallcaps[Lord]] make the woman, who is coming into your house, like #underline[#myhl(women)[Rachel]] and #underline[#myhl(women)[Leah]], who together built up the #myhl(people-groups)[house of Israel]. May you act #underline[worthily] in #underline[#myhl(places)[Ephrathah]] and be renowned in #myhl(places)[Bethlehem], 
#versenum(12) and may your house be like the house of #myhl(men)[Perez], whom #underline[#myhl(women)[Tamar]] bore to #myhl(other)[Judah], because of the offspring that the #myhl(divine)[#smallcaps[Lord]] will give you by this young woman.”


  
#section-heading[Ruth and Boaz Marry]


#versenum(13) So #myhl(men)[Boaz] took #myhl(women)[Ruth], and she became his wife. And he went in to her, and the #myhl(divine)[#smallcaps[Lord]] gave her #underline[conception], and she bore a son. 
#versenum(14) Then the women said to #myhl(women)[Naomi], “Blessed be the #myhl(divine)[#smallcaps[Lord]], who has not left you this day without a redeemer, and may his name be renowned in #myhl(places)[Israel]! 
#versenum(15) He shall be to you a #underline[restorer] of life and a #underline[nourisher] of your old age, for your daughter-in-law who #underline[loves] you, who is more to you than #myhl(numbers)[seven] sons, has given #underline[birth] to him.” 
#versenum(16) Then #myhl(women)[Naomi] took the child and laid him on her #underline[lap] and became his #underline[nurse]. 
#versenum(17) And the women of the neighborhood gave him a name, saying, “A son has been born to #myhl(women)[Naomi].” They named him #myhl(men)[Obed]. He was the father of #myhl(men)[Jesse], the father of #myhl(men)[David].


  
#section-heading[The Genealogy of David]


#versenum(18) Now these are the generations of #myhl(men)[Perez]: #myhl(men)[Perez] fathered #myhl(men)[Hezron], 
#versenum(19) #myhl(men)[Hezron] fathered #myhl(men)[Ram], #myhl(men)[Ram] fathered #myhl(men)[Amminadab], 
#versenum(20) #myhl(men)[Amminadab] fathered #myhl(men)[Nahshon], #myhl(men)[Nahshon] fathered #myhl(men)[Salmon], 
#versenum(21) #myhl(men)[Salmon] fathered #myhl(men)[Boaz], #myhl(men)[Boaz] fathered #myhl(men)[Obed], 
#versenum(22) #myhl(men)[Obed] fathered #myhl(men)[Jesse], and #myhl(men)[Jesse] fathered #myhl(men)[David].



#v(1em)
#align(center)[
  #set text(size: 0.85em)
  Taken from the _ESV® Bible_ (_The Holy Bible, English Standard Version®_),
  Copyright © 2001 by Crossway, a publishing ministry of Good News Publishers.
  Used by permission. All rights reserved.
]
