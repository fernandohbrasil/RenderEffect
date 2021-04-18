# RenderEffect

March was the month of Android 12, preview 2. Among the new features announced, there is the brand new RenderEffect API. In this article, we’re gonna learn which problems this API can solve and how to use it.

Currently, if we need to create a BlurEffect in any View, with an internet search we find some solutions like:

Blurry
```
Blurry.with(context)
  .radius(10)
  .sampling(8)
  .color(Color.argb(66, 255, 255, 0))
  .async()
  .onto(rootView);
```

Glide
```
Glide.with(getContext()).load(R.mipmap.bg)
  .apply(bitmapTransform(new BlurTransformation(22)))
  .into((ImageView) view.findViewById(R.id.imBg));
```

Another alternative would be to create our own complex algorithms like in the following tutorials:
How to Blur Images Efficiently with Android's RenderScript

How to Blur an Image on Android

How to Create a Dynamic Blur Effect on Android 

All of these solutions will deliver what we need. However, some negative points that we should consider are: 
External libs increases the chance of having breaking changes in our project
Creating algorithms increase the complexity of the project
Not efficient use of CPU and RAM.

RenderEffect was created to solve this issue. In Android 12 we don’t need to use an external library or to create a complex algorithm . We just need to add the RenderEffect in the needed View like this:

view.setRenderEffect(
RenderEffect.createBlurEffect(float radiusX, float radiusY, TileMode edgeTreatment
)
)

To remove the RenderEffect you just need to set null in the view, like this:

view.setRenderEffect(null)

Below there is a small sample of the RenderEffect features.

In image 1 we can see the initial state of the app. Just an Image and button that we're gonna see with the BlurEffect applied.


In image 2 we applied the createBlurEffect (float radiusX, float radiusY, Shader.TileMode edgeTreatment) to the button and the Image.

In this method we have the parameters radiusX and radiusY, both are used to define the level and “direction” of the Blur effect. For example, the below image has a radiusY is 20, so you can see the image have a Blur effect in a vertical direction.

Just with these two parameters it is possible to achieve several results, depending on what you need, and just with one line of code.

The parameter edgeTreatment, during my tests I couldn’t identify any difference for this method. So let’s ignore it for now.

In image 3 we are seeing a different effect provided by the RenderEffect. In this case we are using the createBlendModeEffect. This method permits us to combine two different RenderEffect.

In the method createBlendModeEffect(RenderEffect dst, RenderEffect src, BlendMode blendMode), we have two parameters that represent the RenderEffects that we will combine as dst and src. Also, there is a third parameter blendMode that gives us a lot of options and we're gonna see some examples below.

Here is a little example in how we are using the blend mode.
(with a proper text editor I will past the code will look better)

The effects we are blending are the createBlurEffect and the createBitmapEffect. The createBitmapEffect basically adds a bitmap image in the View. So, as we can see we have the original Image with the blur effect applied blended with a new Image superimposing it in the screen.

In image 3 we can see the now known blurEffect with a 20 radiusY + bitmapEffect. Using the blendMode BlendMode.SRC_ATOP.

In image 4 we have just a small change from the previous image. Now the blendMode is BlendMode.DARKEN.

In image 5, is the same as Image 4. But now the blendMode is BlendMode.MULTIPLY.

In image 6, we have a little bigger variation. We applied a blurEffect with 50 in the radiusX and the radiusY, so we can see our original image a lot more blurry. Also the blendMode is BlendMode.HARD_LIGHT.


With only the 3 methods that have been presented, it is possible to achieve several results in a very simple way. Also, the RenderEffect still has other methods which are worth exploring.

A negative point that is worth mentioning is that it seems that this API will not be backported. So only apps with Android 12+ will be able to use this API.

However, it is already very interesting to be able to count on this tool, even for now for a small number of users. In addition to knowing that the Android team is concerned with improvements in this part is also very exciting.


The source code from this article can be found here https://github.com/fernandohbrasil/RenderEffect

Links that I used in this article.

https://android-developers.googleblog.com/2021/03/android-12-developer-preview-2.html

https://developer.android.com/reference/android/graphics/RenderEffect 

https://stackoverflow.com/questions/31641973/how-to-blur-background-images-in-android

https://github.com/wasabeef/Blurry 

https://github.com/bumptech/glide 

https://futurestud.io/tutorials/how-to-blur-images-efficiently-with-androids-renderscript
https://medium.com/mobile-app-development-publication/blurring-image-algorithm-example-in-android-cec81911cd5e
https://medium.com/@Intersog/how-to-create-a-dynamic-blur-effect-on-android-b835d514684

https://developer.android.com/about/versions/12/features

https://blog.stylingandroid.com/rendereffect-blur/
