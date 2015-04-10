brender
=======

Simple, but powerful Renderer pattern implementation for Android Adapters.

Download
--------

### Bundle

Brender comes bundled in `aar` format. Grab the latest bundle from [here](http://search.maven.org/remotecontent?filepath=com/sefford/brender/1.0.3/brender-1.0.3.aar)

### Maven

```XML
<dependency>
    <groupId>com.sefford</groupId>
    <artifactId>brender</artifactId>
    <version>2.0.0</version>
    <type>aar</type>
</dependency>
```

### Gradle 

```groovy
compile 'com.sefford:brender:2.0.0@aar'
```

Why Renderers?
--------------

Renderer pattern decouples the actual models from their representation and encapsulates it on self-contained
classes. 

This reduces dramatically the code base for Adapters, as a large portion of the List and Grid Adapters
can be managed by the `RendererAdapter` on its own.

These `Renderers` are also easily testable as their casuistry is more encapsulated and the methods
can be tested separately. In fact they can be unit tested isolately much more faster than whole 
Adapters, you are advised to make a good use of [Robolectric](http://robolectric.org/), though.

Additionally they model very well the capabilities of the Android Layouts design, and can be extended,
composed and used poliformically. 

The basics
----------

### Implementing a Renderer

Implementing a Renderer is easy. Its lifecycle is very similar to the normal operation of setting a 
view on a normal activity. The default `RendererAdapter` will take most of the work and boilerplate.

The Renderer Lifecycle is as follows:

![Renderer Lifecycle](lifecycle.png)

Brender library provides a basic template class for Renderers with the ID and a `Postable` element.
The Renderers are loosely coupled so the developer can extend their own via the AbstractRenderer
or implementing the `Renderer` interface.

### AdapterDatas

`AdapterData` is an interface which wraps around a data collection to provide an abstraction on the `RendererAdapter`. 

Provides an interface to produce Filters for searching or filtering purposes.

From Brender 2.0.0 it also will detect the number of view types and provide it accordingly to the adapter. This number
needs to be recalculated on `notifyDataSetChanged`, so be aware of large collections.

### Renderer and Renderable IDs

A Renderer will be recyclable when the intended Renderer has the same `Renderer IDs`. 
  
Brender's default implementation will enforce IDs directly selected from *R.layout*. This has some
advantages: 

* Straightforward comparison of Renderers IDs for the RendererBuilders.
* Uniqueness of such IDs, so there are 1-1 relation between layouts and Renderers.

This reduces the code base and the necessity of redundant Renderers. 

These IDs are provided by the model classes by implementing `Renderable` interface.

### Instantiation of Renderers

The default implementation of Brender relies on a factory element with fluent a API which takes
the boilerplate of instantiating the Renderers, as it works inside the `RendererAdapter`.

The developer will be able to delegate the actual creation of Renderers via injecting a `RendererFactory` interface
object to the Builder in construction time. The Builder itself has to be injected to the RendererAdapter,
so several Builders and Factories can persist on several points of the application.

### Communicating back to the UI

The Renderers make use of a `Postable` interface to communicate events to the UI. This design decision
was done in order to avoid lengthy signatures.

This is intended to wrap an Event Bus and post events through it. [Square's Otto](http://square.github.io/otto) or [GreenRobot's EventBus](https://github.com/greenrobot/EventBus)
are highly recommended for this task.

However dynamic factories can be injected to the RendererAdapter configured with local callback methods.

RecyclerView support
--------------------

From version 2.0.0 onwards, Brender supports [RecyclerView](https://developer.android.com/reference/android/support/v7/widget/RecyclerView.html).

To user Brender with RecyclerViews you require to use a `RecyclerRendererAdapter` which provides the same functionality
as the classic `RendererAdapter` but extends from `RecyclerView.Adapter` instead from a `BaseAdapter`. 

Take into account that the basic `AbstractRenderer` implementation no longer will suffice and `Renderers` that are 
required to work with RecyclerViews will need to extend `ViewHolder`. However Renderers that work with RecyclerViews are
backwards compatible with the classic RendererAdapters.

StickyListHeadersListView support
---------------------------------

From version 2.0.0 onwards, Brender supports the popular [StickyListHeader library](https://github.com/emilsjolander/StickyListHeaders) from [Emil Sjölander](http://emilsjolander.se),
making the possibility of using Renderers with the headers too.

In order to do so, you require to set a `StickyHeaderRendererAdapter` to the StickyListHeadersListView. The only difference
with a normal `RendererAdapter` is that you must provide the adapter with a `HeaderIdentifier` instance.
 
### HeaderIdentifier interface

The interface `HeaderIdentifier` should be typically synced to the data in the adapter, and can be targeted to particular
`Renderable` elements if required. A StickyListHeader is normally shared among several objects in a row, so the HeaderIdentifier
can provide a Renderable ID per Renderable and/or position of the data list.

It also wraps the `getHeaderId` method to allow StickyListHeader to provide different instances of the same type of header.

Migrating from Brender 1.0.X to 2.X
------------------------------------

Some parts of Brender have been extensively reworked to simplify and provide an unified interface between ListViews, RecyclerViews
and StickyListHeaderListViews and developers using previous versions should be aware of these changes to accomodate the new
versions of Brender.

### Removed RendererBuilder interface and classes

I found to be overengineering to configure the `Renderer` through a Builder to rely on the `RendererFactory` to
instantiate the Renderer, so I moved the necessary logic inside the Adapters and deleted both the interface and the 
default implementation.

The only requirement now is to provide a RendererFactory interface to the adapter, which is simpler. However, Brender
cannot provide a default implementation, as it is now up to the developer. If you have been using Brender previously, you
can reuse the existing factories.

### Removed extras from RendererFactory.getRenderer() signature

I do not think this was a very useful feature that added some boilerplate code. If you need an ad-hoc configuration for
your Renderers you can do so defining those on `RendererFactory` instances that are initialized locally instead of a
global instance.

### Changed order of flow with Renderer interface

I just found in some cases, some listeners may make use of information loaded at `Renderer.render()` time on the views,
forcing to set up an actionListener on render time. As it should not affect the normal usage of the Renderers, now the
RendererAdapters will first render, then refresh `hookupActionListeners` with the updated information from the `Renderable`
and having the latest view state already configured.

### Removed Renderer.mapViews() from Renderer interface

As it was mandatory to inject the view into the renderer on the RecyclerRendererAdapter, rendering `mapViews` innecessary,
it was removed from the interface. Now all the view initialization should be done at `Renderer` construction time.


### Removed Context from Renderer.render() signature

As you are using a Renderer, which is basically an adapter for Android views, this means you can call `View.getContext()`
any time to produce a usable `Context` instance. In this way, injecting a Context in the signature was redundant, as now
the views are initialized in construction time.

Remember not to store it to avoid possible memory leakage.

### Removed View parameter from Renderer.hookupListeners() signature
 
With RecyclerView compatible `Renderers` requiring to extend `ViewHolder` and passing the View on construction time, it 
is no longer necessary to pass the View on hookupListeners, requiring only the use of a `Renderable` to configure and 
re-set the listeners.

Advanced usage & tips
---------------------

### ButterKnife

[Jake Warthon's ButterKnife](http://jakewharton.github.io/butterknife/) is highly advised to provide comfortable mapViews() code reduction.
 
### Polymorphic views

Remember that as long as a layout has the *exact same views as another one* and is related to the same 
class of the model (i.e User), it is easy to extend one Renderer of another and simply assign it the
correct R.layout ID, producing a different effect and recycling 95% of the code.

### Compositing views

Some views are just built by `<include>` and `<merge>` XML tags. Try to encapsulate such views in layouts
so you can in the future use composition to build a customized Renderer that handles the view without
duplicating code.

In that sense if a view just is a superset of another one, recycle the code by injecting a Renderer
that handles the common elements and then delegate the rest on the new Renderer. Favor composition
over inheritance!

### Reusing views

You can reuse the same Renderer for two different classes of the model if you manage to implement a 
common interface on both.

### Model View Presenters

Renders can act as their own as a kind of Presenters. If you are using the same view for a list than
for a static layout, do not feel afraid to directly use a standalone Renderer to use the dirty work
for you!

On the other hand you can use your Presenters as the backbone for a Renderer, if you see it fit.

### Wait, there is more!

For more cool stuff on Renderers, try [Pedro Vicente's Renderer implementation](https://github.com/pedrovgs/Renderers).

License
-------
    Copyright 2014 Sefford.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.






