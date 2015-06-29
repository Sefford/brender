Changelog
=========

## Brender 2.1.0
_2015-06-29_

* Moved Build system from Maven to Gradle
* Separated the project in two artifacts: brender-base, pure Java and brender, with the Android-dependencies
* Extracted Postable interface to sefford-commons artifact

## Brender 2.0.0
_2015-04-10_

* Added support for RecyclerView-based views
* Added support for StickyListHeaders
* Simplified Renderer interface
* Removed RendererBuilder and integrated its functionality inside the RendererAdapter
* Added itemView support to AdapterData for classic AdapterView support

## Brender 1.0.3
_2014-09-16_

* Added Filtering capabilities to the RendererAdapter 

## Brender 1.0.2
_2014-09-01_

 * Small improvement to the creating renderer process to allow the code to allow the creation code
 to be reused inside the `RendererAdapter` to be used, e.g. in tandem with StickyListHeaders library.
 
## Brender 1.0.1 
_2014-08-20_

 * Fixed bug that was leaking the context
 
## Brender 1.0.0
_2014-06-09_

 * Initial Release